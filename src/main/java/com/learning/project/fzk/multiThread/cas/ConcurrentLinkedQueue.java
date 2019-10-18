package com.learning.project.fzk.multiThread.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用CAS操作实现线程安全的链式队列
 */
public class ConcurrentLinkedQueue<E> {
    private static class Node<E> {
        final E item;
        // 将每个节点的next节点，都包装一层CAS操作
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }

    // 哑结点
    // head永远指向该哑结点，tail当队列为空时指向该哑结点，当队列非空时指向队尾节点
    private final Node<E> dummy = new Node<>(null, null);

    // 头尾节点
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    /**
     * 入队操作：元素插入队尾
     */
    public boolean put(E item) {
        Node<E> newNode = new Node<>(item, null);
        while (true) {
            // 尾节点
            Node<E> curTail = tail.get();
            // 尾节点的next
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) {
                if (tailNext != null) {  // A
                    // 队列处于中间状态，推进尾节点
                    tail.compareAndSet(curTail, tailNext); // B
                } else {
                    // 处于稳定状态，尝试插入新节点
                    if (curTail.next.compareAndSet(null, newNode)) { // C
                        // 插入操作成功，尝试推进尾节点
                        tail.compareAndSet(curTail, newNode); // D
                        return true;
                    }
                }
            }
        }
    }

    /**
     * 出队操作：移出并返回队首元素
     */
    public E pool() {
        // TODO
        return null;
    }
}