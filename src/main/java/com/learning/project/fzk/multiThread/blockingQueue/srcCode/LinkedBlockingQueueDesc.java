package com.learning.project.fzk.multiThread.blockingQueue.srcCode;

public class LinkedBlockingQueueDesc {
    /*
    LinkedBlockingQueue:
         * A variant of the "two lock queue" algorithm.  The putLock gates
         * entry to put (and offer), and has an associated condition for
         * waiting puts.  Similarly for the takeLock.  The "count" field
         * that they both rely on is maintained as an atomic to avoid
         * needing to get both locks in most cases. Also, to minimize need
         * for puts to get takeLock and vice-versa, cascading notifies are
         * used. When a put notices that it has enabled at least one take,
         * it signals taker. That taker in turn signals others if more
         * items have been entered since the signal. And symmetrically for
         * takes signalling puts. Operations such as remove(Object) and
         * iterators acquire both locks.
         * 这是一种“双锁队列算法”的变种。一把锁负责锁住put/offer操作，同时有一个
         * 对象监视器（condition）与这个锁关联，对于take端同理。
         * 而对于take操作和put操作同时依赖的队列长度（count）变量，则使用atomic方式来
         * 避免线程安全问题。并且，为了让put操作尽量少的去拿take锁，让take操作尽量少的去拿put锁，
         * 这里使用了级联唤醒（cascading notifies）。当put操作唤醒（signals）了至少一个take操作，
         * 那么这个take操作会在队列中还有其他内容的情况下主动唤醒其他take操作，反之亦然。
         * 对于移除和迭代器这些操作则需要获取两把锁
         *
         * Whenever an element is enqueued, the putLock is acquired and
         * count updated.  A subsequent reader guarantees visibility to the
         * enqueued Node by either acquiring the putLock (via fullyLock)
         * or by acquiring the takeLock, and then reading n = count.get();
         * this gives visibility to the first n items.
         * 当一个元素入队，则需要一个put锁（putlock）以及队列长度+1（count update）
         * 随后的reader通过以下方式实现对刚入队的结点的可见性:
         *  1、要么获取一个put锁
         *  2、要么获取一个take锁且读n个结点
         *
         * To implement weakly consistent iterators, it appears we need to
         * keep all Nodes GC-reachable from a predecessor dequeued Node.
         * That would cause two problems:
         * - allow a rogue Iterator to cause unbounded memory retention
         * - cause cross-generational linking of old Nodes to new Nodes if
         *   a Node was tenured while live, which generational GCs have a
         *   hard time dealing with, causing repeated major collections.
         * However, only non-deleted Nodes need to be reachable from
         * dequeued Nodes, and reachability does not necessarily have to
         * be of the kind understood by the GC.  We use the trick of
         * linking a Node that has just been dequeued to itself.  Such a
         * self-link implicitly means to advance to head.next.
         * 为了使弱一致性的迭代器可用，我们需要每一个结点只要出队都可以被JC回收
         * 不然会有以下两个问题
         *  1、可能会导致iterator造成无止境的内存泄露
         *  2、如果一个结点一直在内存中存活，可能导致跨代之间结点相连，这会让GC很难处理这些结点的回收
         *  所幸的是，在所有出队的结点中只有未被删除的结点需要被我们考虑在内，保证他们可以被GC正确回收
         *  且这些结点没有必要做到对GC而言一目了然的可以被认作是垃圾结点。因此，这里我们
         *  使用了一个比较取巧的方法，即我们讲离队的结点自己连接自己，这个自我连接即.next指向自己
    */
}
