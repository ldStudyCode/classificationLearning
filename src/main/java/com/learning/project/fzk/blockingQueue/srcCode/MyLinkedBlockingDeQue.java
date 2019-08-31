package com.learning.project.fzk.blockingQueue.srcCode;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 双链表阻塞队列
 * 特性：使用单个锁保证线程安全，无法同时出队、入队，性能较低
 *
 * author:fanzhoukai
 * 2019/8/17 15:09
 */
public class MyLinkedBlockingDeQue<E> {


	/** 双链表节点 */
	static final class Node<E> {
		// 当前元素。可空
		E item;
		// 真实的前驱节点 / 当前节点，表示链表末尾 / 空，表示没有前驱节点
		Node<E> prev;
		// 真实的后继节点 / 当前节点，表示后继节点是头 / 空，表示没有后继节点
		Node<E> next;

		Node(E x) {
			item = x;
		}
	}

	/**
	 * 头结点指针
	 * Invariant: (first == null && last == null) ||
	 *            (first.prev == null && first.item != null)
	 */
	transient Node<E> first;

	/**
	 * 尾节点指针
	 * Invariant: (first == null && last == null) ||
	 *            (last.next == null && last.item != null)
	 */
	transient Node<E> last;

	// 队列元素数量
	private transient int count;

	// 队列最大容量
	private final int capacity;

	// 监视所有访问的锁对象
	final ReentrantLock lock = new ReentrantLock();

	// 等待“取出”操作的condition
	private final Condition notEmpty = lock.newCondition();
	// 等待“放入”操作的condition
	private final Condition notFull = lock.newCondition();

	/**
	 * 构造方法
	 */
	public MyLinkedBlockingDeQue() {
		this(Integer.MAX_VALUE);
	}
	/**
	 * @param capacity 队列最大容量，默认为Integer#MAX_VALUE
	 */
	public MyLinkedBlockingDeQue(int capacity) {
		if (capacity <= 0) throw new IllegalArgumentException();
		this.capacity = capacity;
	}
	/**
	 * @param c 队列初始包含的元素
	 */
	public MyLinkedBlockingDeQue(Collection<? extends E> c) {
		this(Integer.MAX_VALUE);
		final ReentrantLock lock = this.lock;
		lock.lock(); // Never contended, but necessary for visibility
		try {
			for (E e : c) {
				if (e == null)
					throw new NullPointerException();
				if (!linkLast(new Node<E>(e)))
					throw new IllegalStateException("Deque full");
			}
		} finally {
			lock.unlock();
		}
	}

	// 获取队列元素个数
	public int size() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			return count;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 添加元素至队尾，失败返回false
	 */
	public boolean offer(E e) {
		if (e == null) throw new NullPointerException();
		// 创建节点对象
		Node<E> node = new Node<E>(e);
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			return linkLast(node);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 添加元素，等待指定时间，失败返回false
	 */
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		if (e == null) throw new NullPointerException();
		Node<E> node = new Node<E>(e);
		long nanos = unit.toNanos(timeout);
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			while (!linkLast(node)) {
				if (nanos <= 0)
					return false;
				nanos = notFull.awaitNanos(nanos);
			}
			return true;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 添加元素，队列已满会阻塞
	 */
	public void put(E e) throws InterruptedException {
		if (e == null) throw new NullPointerException();
		Node<E> node = new Node<E>(e);
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			while (!linkLast(node))
				notFull.await();
		} finally {
			lock.unlock();
		}
	}

	// 弹出队首元素，队列为空会阻塞，直到拿出来为止
	public E take() throws InterruptedException {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			E x;
			while ((x = unlinkFirst()) == null)
				notEmpty.await();
			return x;
		} finally {
			lock.unlock();
		}
	}

	// 弹出首个元素，等待指定时间
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		long nanos = unit.toNanos(timeout);
		final ReentrantLock lock = this.lock;
		lock.lockInterruptibly();
		try {
			E x;
			while ( (x = unlinkFirst()) == null) {
				if (nanos <= 0)
					return null;
				nanos = notEmpty.awaitNanos(nanos);
			}
			return x;
		} finally {
			lock.unlock();
		}
	}

	// 查看队列首个元素
	public E peek() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			return (first == null) ? null : first.item;
		} finally {
			lock.unlock();
		}
	}

	// 把指定节点连接到队首，若队列已满，返回false
	private boolean linkFirst(Node<E> node) {
		// assert lock.isHeldByCurrentThread();
		if (count >= capacity)
			return false;
		Node<E> f = first;
		node.next = f;
		first = node;
		if (last == null)
			last = node;
		else
			f.prev = node;
		++count;
		notEmpty.signal();
		return true;
	}

	// 把指定节点连接至队尾，若队列已满，返回false
	private boolean linkLast(Node<E> node) {
		if (count >= capacity)
			return false;
		Node<E> l = last;
		node.prev = l;
		last = node;
		if (first == null)
			first = node;
		else
			l.next = node;
		++count;
		notEmpty.signal();
		return true;
	}

	// 移除并返回队首元素，队列为空返回null
	private E unlinkFirst() {
		Node<E> f = first;
		if (f == null)
			return null;
		Node<E> n = f.next;
		E item = f.item;
		f.item = null; // help GC
		f.next = f;
		first = n;
		if (n == null)
			last = null;
		else
			n.prev = null;
		--count;
		notFull.signal();
		return item;
	}

	// 移除并返回队尾元素，队列为空返回null
	private E unlinkLast() {
		Node<E> l = last;
		if (l == null)
			return null;
		Node<E> p = l.prev;
		E item = l.item;
		l.item = null; // help GC
		l.prev = l;
		last = p;
		if (p == null)
			first = null;
		else
			p.next = null;
		--count;
		notFull.signal();
		return item;
	}

	/**
	 * Returns {@code true} if this deque contains the specified element.
	 * More formally, returns {@code true} if and only if this deque contains
	 * at least one element {@code e} such that {@code o.equals(e)}.
	 *
	 * @param o object to be checked for containment in this deque
	 * @return {@code true} if this deque contains the specified element
	 */
	public boolean contains(Object o) {
		if (o == null) return false;
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			for (Node<E> p = first; p != null; p = p.next)
				if (o.equals(p.item))
					return true;
			return false;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Returns an array containing all of the elements in this deque, in
	 * proper sequence (from first to last element).
	 *
	 * <p>The returned array will be "safe" in that no references to it are
	 * maintained by this deque.  (In other words, this method must allocate
	 * a new array).  The caller is thus free to modify the returned array.
	 *
	 * <p>This method acts as bridge between array-based and collection-based
	 * APIs.
	 *
	 * @return an array containing all of the elements in this deque
	 */
	@SuppressWarnings("unchecked")
	public Object[] toArray() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			Object[] a = new Object[count];
			int k = 0;
			for (Node<E> p = first; p != null; p = p.next)
				a[k++] = p.item;
			return a;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Returns an array containing all of the elements in this deque, in
	 * proper sequence; the runtime type of the returned array is that of
	 * the specified array.  If the deque fits in the specified array, it
	 * is returned therein.  Otherwise, a new array is allocated with the
	 * runtime type of the specified array and the size of this deque.
	 *
	 * <p>If this deque fits in the specified array with room to spare
	 * (i.e., the array has more elements than this deque), the element in
	 * the array immediately following the end of the deque is set to
	 * {@code null}.
	 *
	 * <p>Like the {@link #toArray()} method, this method acts as bridge between
	 * array-based and collection-based APIs.  Further, this method allows
	 * precise control over the runtime type of the output array, and may,
	 * under certain circumstances, be used to save allocation costs.
	 *
	 * <p>Suppose {@code x} is a deque known to contain only strings.
	 * The following code can be used to dump the deque into a newly
	 * allocated array of {@code String}:
	 *
	 *  <pre> {@code String[] y = x.toArray(new String[0]);}</pre>
	 *
	 * Note that {@code toArray(new Object[0])} is identical in function to
	 * {@code toArray()}.
	 *
	 * @param a the array into which the elements of the deque are to
	 *          be stored, if it is big enough; otherwise, a new array of the
	 *          same runtime type is allocated for this purpose
	 * @return an array containing all of the elements in this deque
	 * @throws ArrayStoreException if the runtime type of the specified array
	 *         is not a supertype of the runtime type of every element in
	 *         this deque
	 * @throws NullPointerException if the specified array is null
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			if (a.length < count)
				a = (T[])java.lang.reflect.Array.newInstance
						(a.getClass().getComponentType(), count);

			int k = 0;
			for (Node<E> p = first; p != null; p = p.next)
				a[k++] = (T)p.item;
			if (a.length > k)
				a[k] = null;
			return a;
		} finally {
			lock.unlock();
		}
	}

	public String toString() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			Node<E> p = first;
			if (p == null)
				return "[]";

			StringBuilder sb = new StringBuilder();
			sb.append('[');
			for (;;) {
				E e = p.item;
				sb.append(e == this ? "(this Collection)" : e);
				p = p.next;
				if (p == null)
					return sb.append(']').toString();
				sb.append(',').append(' ');
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Atomically removes all of the elements from this deque.
	 * The deque will be empty after this call returns.
	 */
	public void clear() {
		final ReentrantLock lock = this.lock;
		lock.lock();
		try {
			for (Node<E> f = first; f != null; ) {
				f.item = null;
				Node<E> n = f.next;
				f.prev = null;
				f.next = null;
				f = n;
			}
			first = last = null;
			count = 0;
			notFull.signalAll();
		} finally {
			lock.unlock();
		}
	}
}
