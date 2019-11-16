package com.learning.project.fzk.juc;

import com.learning.project.fzk.juc.lock.MyReentrantReadWriteLock;
import com.learning.project.fzk.juc.lock.MySimpleReentrantLock;
import com.learning.project.fzk.multiThread.lock.ReadWriteMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;

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
		Condition condition = lock.newCondition();
		lock.lock();
		try {
			condition.await();
			condition.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 测试读写锁ReentrantReadWriteLock
	 *
	 * @see ReadWriteMap
	 */
	@Test
	public void testReentrantReadWriteLock() {
		ReadWriteMap<String,String> map = new ReadWriteMap();
		map.put("a","a"); // 自动上写锁
		map.get("a"); // 自动上读锁
	}

	/**
	 * 测试增强版读写锁StampedLock
	 */
	@Test
	public void testStampedLock() {

	}
}
