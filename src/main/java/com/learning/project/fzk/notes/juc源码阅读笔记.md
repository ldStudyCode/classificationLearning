[TOC]

> 参考资料：
>
> [死磕java并发系列](http://cmsblogs.com/?cat=151) 
>
> [透彻理解Java并发编程系列](https://segmentfault.com/blog/ressmix_multithread) 



# 0. juc包源码结构

在juc包中，很多类都依赖于 `Unsafe` 类提供的CAS方法。直接依赖的包括两部分：第一类是lock包中的AQS同步器，第二类是atomic包中的16个原子性更新工具类。在它们之上，是各种锁（locks包内），以及各种并发工具类（直接包含于juc包）。源码结构如下：

* **AQS**（juc.locks包内）

  * AQS同步器：AbstractQueuedSynchronizer、AbstractQueuedLongSynchronizer
  * 各种锁结构
    * 互斥锁：ReentrantLock
    * 读写锁：ReentrantReadWriteLock
    * 增强版读写锁：StampedLock

* **CAS**（juc.atomic包内）
* 标量类：AtomicInteger、AtomicLong、AtomicBoolean、AtomicReference
  * 增强版标量类：DoubleAccumulator、DoubleAdder、LongAccumulator、LongAdder
  * 原子数组类：AtomicIntegerArray、AtomicLongArray、AtomicReferenceArray
  * 域更新器类：AtomicIntegerFieldUpdater、AtomicLongFieldUpdater、AtomicReferenceFieldUpdater
  * 复合变量类：AtomicStampedReference、AtomicMarkableReference
  
* **并发工具类**（juc包内）

  * 阻塞队列：ArrayBlockingQueue、DelayQueue、LinkedBlockingDeque、LinkedBlockingQueue、LinkedTransferQueue、PriorityBlockingQueue、SynchronousQueue
  * 并发数据结构：ConcurrentHashMap、ConcurrentLinkedDeque、ConcurrentLinkedQueue、ConcurrentSkipListMap、ConcurrentSkipListSet、CopyOnWriteArrayList、CopyOnWriteArraySet
  * 线程池：Executors、ScheduledThreadPoolExecutor、ThreadPoolExecutor、ForkJoinPool
  * 异步计算：FutureTask、CompletableFuture、CompletionService、ExecutorCompletionService
  * 闭锁：CountDownLatch
  * 信号量：Semaphore
  * 栅栏：CyclicBarrier、Exchanger、Phaser

  * 随机数生成器：ThreadLocalRandom



此外，还有一些类处于继承关系的中间层，以及一些接口、异常类，没有在上面列出来。

源码阅读的版本为jdk1.8。



# 1. AQS

## 1.1 CLH同步队列

AQS底层是一套封装好的信号量和PV操作，且对于PV操作的阻塞机制，使用了一个队列结构来保存那些阻塞中的线程。这个队列使用链表实现，且要保证它的入队和出队的线程安全性，使用一种称为CLH队列的结构来实现。

CLH队列的节点结构与普通队列一样，除了保存前驱和后继节点外，还会有一个Thread对象存储当前线程。其他与队列结构无关的属性（如节点状态等）没有写出来。如下：

```java
static final class Node {
  volatile Node prev;
  volatile Node next;
  volatile Thread thread;
}
```

### 1.1.1 工作流程

由于可能发生多个线程同时入队的情况，因此需要保证入队操作的线程安全性。而且因为AQS本身就是用于实现互斥锁的，我们还不能使用互斥锁，因此只能通过正确的CAS操作来保证线程安全。

队列使用了一个虚拟头结点（哑结点），几种状态如下：

1. 空队列：

   ```
   head    tail
   null    null
   ```

2. 队列中有一个元素：

   ```
   head    tail
   dummy   node1
   ```

3. 队列中有多个元素：

   ```
   head                        tail
   dummy   node1   node2   ... nodeN
   ```

基于CAS的入队操作流程如下：

TODO 画一个好看点的图

![212c576d625a1d8a5a4f2201dfb25f0.jpg](https://i.loli.net/2019/11/01/6TEr5XRHFbyxBzu.jpg)



源代码如下：

```java
// 入队
private Node enq(final Node node) {
  for (;;) {
    Node t = tail;
    if (t == null) {
      if (compareAndSetHead(new Node()))
        tail = head;
    } else {
      node.prev = t;
      if (compareAndSetTail(t, node)) {
        t.next = node;
        return t;
      }
    }
  }
}

// 出队，通过头结点后移来实现
private void setHead(Node node) {
  head = node;
  node.thread = null;
  node.prev = null;
}
```



### 1.1.2 思考

**思考1：当判断队列是否初始化时，为什么使用tail节点来判断，而不是head节点？**

这与初始化链表的过程相关。注意未初始化时的流程，第一步CAS设置head，成功了再设置tail。也就是说，如果head设置成功，不一定保证tail也设置成功，这是两步线程不安全的操作，但如果tail已经设置成功了，则一定保证链表构造完成。



**思考2：已初始化流程中，若第二步CAS设置tail成功，这时连续出队，会有线程安全问题吗？**

不会有线程安全问题，因为出队操作只有一步：`head = head.next` ，假设第二步CAS设置tail成功，这时连续执行出队，最终链表结构是这样：

```
head      tail
dummy <-- node
```

注意其中dummy到新插入的node节点的连接还没有建立，而这时已经没法再执行出队操作了。这时一定会等新node完全插入后，再执行出队操作（因为它插入后本身就是队首元素）。



**思考3：出队操作会有线程安全问题吗？**

不会。尽管出队过程没有CAS等同步措施，但仍然是线程安全的。

AQS中的CLH队列与阻塞队列不同，阻塞队列的出队过程是：多个线程同时阻塞着，等待获取队首元素。而CLH队列的出队过程是：队列中每个节点都是一个线程，且没有任何其他线程等待出队，因此出队过程是没有竞争的——队首节点慢悠悠的完成出队，才会唤醒下一个节点去抢锁。因此，出队操作不需要保证线程安全。



## 1.2 Condition的实现

TODO



参考资料：

AQS论文：[The java.util.concurrent Synchronizer Framework](http://gee.cs.oswego.edu/dl/papers/aqs.pdf) 

论文翻译：[The j.u.c Synchronizer Framework翻译](https://my.oschina.net/clopopo/blog/121920) 

死磕Java并发系列：[AQS简介](http://cmsblogs.com/?p=2174) 、 [CLH同步队列](http://cmsblogs.com/?p=2188) 、 [同步状态的获取与释放](http://cmsblogs.com/?p=2197) 、 [阻塞和唤醒线程](http://cmsblogs.com/?p=2205) 

源码讲解：[从源码角度彻底理解ReentrantLock(重入锁)](https://www.cnblogs.com/takumicx/p/9402021.html) 



# 2. ReentrantLock

ReentrantLock是利用AQS的PV操作，实现的互斥锁结构。通过上一节我们知道，AQS内部维护了一个阻塞中的线程队列，因此很容易想到公平锁的实现——线程若获取不到锁，直接排队等候即可，可以保证公平性。那么如何使用一个公平的队列，实现非公平锁呢？就是在每次入队前，都使用CAS尝试一次获取锁。如果获取成功，则相当于插了CLH队列中线程的队。通过这样一种“插队”机制，实现了非公平的特性。下面以非公平锁的流程为例，讲解ReentrantLock。

## 2.1 工作流程

TODO 画一个好看点的图

![8606c2a1476378bd22a819858cee2b4.jpg](https://i.loli.net/2019/11/01/XVDR3cOIw7UCZSr.jpg)

## 2.2 思考

**思考1：ReentrantLock如何实现偏向锁、轻量级锁、重量级锁的膨胀？**

偏向锁是指，同一线程多次上锁时，只需要执行一次CAS操作，而不需要每次上锁都执行一次CAS。

获取锁时，会首先判断当前状态，如果锁计数器==0，说明没有线程正在竞争该锁，则通过CAS设置该状态值为1，则可以预见其他任何线程调用CAS都不会再成功，也就认为当前线程得到了该锁，很显然这个线程并未进入等待队列，因此实现了【轻量级锁】。

如果锁计数器不等于0，说明有线程拥有了该锁。 只是简单地将计数器+1，但因为没有竞争，所以通过非CAS（即线程不安全）的方式修改，即实现了【偏向锁】的功能。

若以上步骤失败，则会直接进入CLH队列中阻塞，即膨胀为【重量级锁】。

























