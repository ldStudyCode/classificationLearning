package com.learning.project.fzk.multiThread.blockingQueue.test;

import com.learning.project.fzk.multiThread.blockingQueue.srcCode.MyLinkedBlockingQueue;
import org.junit.Assert;
import org.junit.Test;

/**
 * 对MyLinkedBlockingQueue的阻塞测试
 */
public class TestBlock {

    @Test
    public void test() throws InterruptedException {
        final int capacity = 10;
        MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>(capacity);

        Thread thread = new Thread(() -> {
            try {
                // 空队列执行take，会一直阻塞
                queue.take();
                // 此行不会被执行到
                Assert.fail("阻塞被唤醒");
            } catch (InterruptedException e) {
                System.out.println("线程被成功中断");
            }
        });

        thread.start(); // 启动线程
        Thread.sleep(1000); // 让thread阻塞一会
        thread.interrupt(); // 中断thread
        thread.join(1000); // 等待thread执行完。理论上会执行完System.out.println("线程被成功中断");这一句后立刻跳出，不会阻塞
        Assert.assertFalse(thread.isAlive());
    }
}
