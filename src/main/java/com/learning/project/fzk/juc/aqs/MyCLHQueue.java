package com.learning.project.fzk.juc.aqs;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 基于CAS实现的线程安全的队列结构，用于AQS同步器
 * CLH队列算法，是AQS同步器中的简化版，只保留了其中CLH队列相关的代码
 */
public class MyCLHQueue {

    private static final Unsafe unsafe = getUnsafe();
    // 队列头指针，延迟初始化。除了初始化之外，只有setHead()方法能修改它。
    private transient volatile Node head;

    // 队列尾指针，延迟初始化。只有通过enq()方法能添加等待的线程节点
    private transient volatile Node tail;

    // 头尾指针的偏移量，用于CAS操作定位属性内存地址
    private static final long headOffset;
    private static final long tailOffset;

    // 初始化头尾指针内存地址偏移量
    static {
        try {
            headOffset = unsafe.objectFieldOffset(MyCLHQueue.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset(MyCLHQueue.class.getDeclaredField("tail"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    /**
     * node节点插入队尾。需要时可以初始化，见上面的图
     *
     * @param node 待插入的节点
     * @return 节点的前一个节点（即原有的末尾节点）
     */
    private Node enq(final Node node) {
        for (; ; ) {
            Node t = tail;
            if (t == null) {
                // tail为空，说明链表还未构建，因此必须初始化
                if (compareAndSetHead(new Node())) // 创建哑结点，并CAS设置到head上；同时设置到tail上。若CAS失败，则重新走循环
                    tail = head;
            } else {
                // tail非空，说明链表已经构建完成，可以直接将新node插入tail后面了
                node.prev = t;
                if (compareAndSetTail(t, node)) { // 只要CAS设置tail成功，就说明共享的tail指针已经变更完成了，就可以安心地设置原tail的next节点了
                    t.next = node; // 这里即使和上一步CAS之间插入了其他线程的操作也没问题
                    return t;
                }
            }
        }
    }

    /**
     * 设置head节点，来实现出队操作。只会被acquire方法调用
     */
    private void setHead(Node node) {
        head = node;
        node.prev = null;
    }

    // CAS更新头结点
    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }

    // CAS更新尾结点
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    // 通过反射获取Unsafe实例
    private static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            return (Unsafe) f.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

final class Node {
    volatile Node prev;
    volatile Node next;

    Node() {
    }
}