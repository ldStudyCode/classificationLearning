package com.learning.project.fzk.blockingQueue.test;

import com.learning.project.fzk.blockingQueue.srcCode.MyLinkedBlockingQueue;
import org.junit.Assert;
import org.junit.Test;

/**
 * 对MyLinkedBlockingQueue的基本单元测试
 */
public class TestWork {

    /**
     * 基本的单元测试
     */
    @Test
    public void testWork() throws InterruptedException {
        final int capacity = 10;
        MyLinkedBlockingQueue<Integer> queue = new MyLinkedBlockingQueue<>(capacity);
        // 测试：新创建的队列为空
        Assert.assertTrue(queue.size() == 0);

        for (int i = 0; i < capacity; i++) {
            queue.put(i);
        }
        // 测试：队列元素数量等于插入操作的次数
        Assert.assertTrue(queue.size() == capacity);
    }
}
