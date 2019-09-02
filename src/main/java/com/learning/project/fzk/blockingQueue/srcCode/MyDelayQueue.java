package com.learning.project.fzk.blockingQueue.srcCode;

import java.util.PriorityQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * 使用优先队列实现的无界延迟阻塞队列
 *
 * author:fanzhoukai
 * 2019/8/24 10:15
 */
public class MyDelayQueue<E extends Delayed> {

	private final transient ReentrantLock lock = new ReentrantLock();
	// 优先队列
	private final PriorityQueue<E> q = new PriorityQueue<E>();

	/**
	 * 等待队首元素的线程。这是Leader-Follower线程模型的变体，用于减少不必要的时间等待。
	 *
	 * 当一个线程成为leader时，它只会阻塞到堆顶元素过期，而其他线程会无限期等待。
	 *
	 * leader线程必须在返回take或poll之前，级联唤醒其他线程。除非其他线程成为leader。
	 */
	private Thread leader = null;

	/**
	 * 当头结点元素可用时，会被唤醒。
	 * 或者新线程将成为leader。
	 * Condition signalled when a newer element becomes available
	 * at the head of the queue or a new thread may need to
	 * become leader.
	 */
	private final Condition available = lock.newCondition();

	/**
	 * 构造方法
	 */
	public MyDelayQueue() {}

	/**
	 * 元素入队列。保证插入一定成功
	 */
	public void offer(E e) {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			q.offer(e);
			if (q.peek() == e) {
				leader = null;
				available.signal();
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 返回并移除队首元素，若队列没有满足延迟条件的元素，返回null
	 */
	public E poll() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			E first = q.peek();
			if (first == null || first.getDelay(NANOSECONDS) > 0)
				return null;
			else
				return q.poll();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 返回并移除队首元素，若队列没有满足延迟条件的元素，阻塞指定时间
	 */
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		long nanos = unit.toNanos(timeout);
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			for (;;) {
				E first = q.peek();
				if (first == null) {
					if (nanos <= 0)
						return null;
					else
						nanos = available.awaitNanos(nanos);
				} else {
					long delay = first.getDelay(NANOSECONDS);
					if (delay <= 0)
						return q.poll();
					if (nanos <= 0)
						return null;
					first = null; // don't retain ref while waiting
					if (nanos < delay || leader != null)
						nanos = available.awaitNanos(nanos);
					else {
						Thread thisThread = Thread.currentThread();
						leader = thisThread;
						try {
							long timeLeft = available.awaitNanos(delay);
							nanos -= delay - timeLeft;
						} finally {
							if (leader == thisThread)
								leader = null;
						}
					}
				}
			}
		} finally {
			if (leader == null && q.peek() != null)
				available.signal();
			lock.unlock();
		}
	}

	/**
	 * 返回并移除队首元素，若队列没有满足延迟条件的元素，阻塞
	 */
	public E take() throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			for (;;) {
				E first = q.peek();
				if (first == null)
					available.await();
				else {
					long delay = first.getDelay(NANOSECONDS);
					if (delay <= 0)
						return q.poll();
					first = null; // don't retain ref while waiting
					if (leader != null)
						available.await();
					else {
						Thread thisThread = Thread.currentThread();
						leader = thisThread;
						try {
							available.awaitNanos(delay);
						} finally {
							if (leader == thisThread)
								leader = null;
						}
					}
				}
			}
		} finally {
			if (leader == null && q.peek() != null)
				available.signal();
			lock.unlock();
		}
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, or
	 * returns {@code null} if this queue is empty.  Unlike
	 * {@code poll}, if no expired elements are available in the queue,
	 * this method returns the element that will expire next,
	 * if one exists.
	 *
	 * @return the head of this queue, or {@code null} if this
	 *         queue is empty
	 */
	public E peek() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			return q.peek();
		} finally {
			lock.unlock();
		}
	}

	public int size() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			return q.size();
		} finally {
			lock.unlock();
		}
	}
}