package com.learning.project.fzk.multiThread.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用CAS操作实现线程安全的栈结构
 */
public class ConcurrentStack<E> {
    // 栈顶元素。包装在原子变量内，以实现CAS操作
    private AtomicReference<Node<E>> top = new AtomicReference<>();

    /**
     * 元素放入栈顶
     */
    public void push(E item) {
        // 创建新元素的节点
        Node<E> newHead = new Node<>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead)); // top==oldHead，说明没有线程竞争，允许更新
    }

    /**
     * 弹出栈顶元素
     */
    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null)
                return null;
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }
}