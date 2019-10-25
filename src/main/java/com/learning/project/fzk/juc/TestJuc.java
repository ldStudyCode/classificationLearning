package com.learning.project.fzk.juc;

import com.learning.project.fzk.juc.lock.MySimpleReentrantLock;
import org.junit.Test;

/**
 * juc包单元测试类
 */
public class TestJuc {
    /**
     * 测试互斥锁
     */
    @Test
    public void testMySimpleReentrantLock() {
        MySimpleReentrantLock lock = new MySimpleReentrantLock();
        lock.lock();
        try {

        }finally {
            lock.unlock();
        }
    }
}
