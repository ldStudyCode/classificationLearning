package com.learning.project.fzk.lock;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized、ReentrantLock语法示例
 * <p>
 * author:fanzhoukai
 * 2019/8/31 14:10
 */
public class LockSample {
	/**
	 * synchronized锁
	 */
	@Test
	public void testSynchronized() throws Exception {
		Object lock = new Object();

		Thread thread1 = new Thread(() -> {
			synchronized (lock) {
				System.out.println("Thread1:获取到锁");
				try {
					Thread.sleep(1000);
					System.out.println("Thread1:睡了一会，不释放锁");

					System.out.println("Thread1:wait方法，要释放锁了，要被Thread2抢到了！");
					lock.wait();
					System.out.println("Thread1:wait方法被唤醒，重新获取到锁！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread1:解锁！");
			}
		});


		Thread thread2 = new Thread(() -> {
			synchronized (lock) {
				System.out.println("Thread2:终于抢到锁！");
				lock.notify();
				System.out.println("Thread2:我还能走！notify并不会立即唤醒，要等到当前线程释放锁后再唤醒别人！");
				System.out.println("Thread2:解锁！");
			}
		});

		thread1.start();
		Thread.sleep(200); // 确保thread1先抢执行，先到锁
		thread2.start();
		Thread.sleep(3000); // 等待所有线程执行完成
	}

	/**
	 * ReentrantLock锁
	 */
	@Test
	public void testReentrantLock() throws InterruptedException {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();

		Thread thread1 = new Thread(() -> {
			lock.lock();
			System.out.println("Thread1:获取到锁");
			try {
				Thread.sleep(1000);
				System.out.println("Thread1:睡了一会，不释放锁");

				System.out.println("Thread1:await方法，要释放锁了，要被Thread2抢到了！");
				condition.await();
				System.out.println("Thread1:wait方法被唤醒，重新获取到锁！");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				System.out.println("Thread1:解锁！");
				lock.unlock();
			}
		});


		Thread thread2 = new Thread(() -> {
			lock.lock();
			try {
				System.out.println("Thread2:终于抢到锁！");
				condition.signal();
				System.out.println("Thread2:我还能走！signal并不会立即唤醒，要等到当前线程释放锁后再唤醒别人！");
			}finally {
				System.out.println("Thread2:解锁！");
				lock.unlock();
			}

		});

		thread1.start();
		Thread.sleep(200); // 确保thread1先抢执行，先到锁
		thread2.start();
		Thread.sleep(3000); // 等待所有线程执行完成
	}
}
