package com.learning.project.fzk.blockingQueue.test;

import com.learning.project.fzk.blockingQueue.srcCode.MyLinkedBlockingQueue;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对MyLinkedBlockingQueue的线程安全测试
 * <p>
 * 思路：使用jdk自带的线程池来模拟并发，如：对于一个容量为10的阻塞队列，使用10个线程，同时执行1000000次入队、出队操作。
 * 每次入队的元素是一个随机数，且每次入队出队，都将插入/移出的随机数累加。
 * 若最终多线程插入/移出的累加数字相等，则说明没有产生线程安全问题。
 */
public class TestThreadSave {
    int capacity = 10; // 队列容量
    int threadNum = 10; // 线程数
    int operateNum = 1000000; // 操作数，即每个线程内执行的take/put操作的数量

    // 待测试的阻塞队列
    MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>(capacity);
    // 可把结构改为线程不安全的LinkedList，则无法通过测试
//    LinkedList<Integer> queue = new LinkedList<Integer>();

    // 线程池
    ExecutorService pool = Executors.newCachedThreadPool();
    // 各线程插入的所有元素的累加器
    AtomicInteger putSum = new AtomicInteger();
    // 各线程移出的所有元素的累加器
    AtomicInteger takeSum = new AtomicInteger();

    // 线程栅栏：为了强迫所有线程处于同一起跑线，然后同时开始take/put
    CyclicBarrier barrier = new CyclicBarrier(threadNum * 2 + 1);


    /**
     * 测试入口函数
     */
    @Test
    public void test() throws BrokenBarrierException, InterruptedException {
        for (int i = 0; i < threadNum; i++) {
            pool.execute(new Producer());
            pool.execute(new Consumer());
        }
        barrier.await(); // 等待所有线程就绪
        barrier.await(); // 等待所有线程执行结束
        // 若最终多线程插入/移出的累加数字相等，则说明没有产生线程安全问题
        Assert.assertEquals(putSum.get(), takeSum.get());
        System.out.println("完成!");
    }

    /**
     * 生产者线程
     */
    class Producer implements Runnable {

        @Override
        public void run() {
            try {
                int seed = this.hashCode() ^ (int) System.nanoTime(); // 初始化随机数种子
                int sum = 0; // 插入元素的累加器

                barrier.await(); // 表明当前线程已就绪，随时可以开始执行
                for (int i = 0; i < operateNum; i++) {
                    queue.put(seed); // 插入元素
                    sum += seed; // 执行累加器
                    seed = xorShift(seed); // 生成新的随机数
                }
                putSum.getAndAdd(sum);
                barrier.await(); // 表明当前线程已执行完，结果已存入共享累加器中
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 消费者线程
     */
    class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                int sum = 0; // 移出元素的累加器
                barrier.await(); // 表明当前线程已就绪，随时可以开始执行
                for (int i = 0; i < operateNum; i++) {
                    sum += queue.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await(); // 表明当前线程已执行完，结果已存入共享累加器中
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 随机数生成器
     * TODO 看xorShift算法原理
     */
    static int xorShift(int x) {
        x ^= (x << 6);
        x ^= (x >>> 21);
        x ^= (x << 7);
        return x;
    }
}

