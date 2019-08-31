package com.learning.project.fzk.blockingQueue.srcCode;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 单链表阻塞队列
 * 特性：支持同时入队出队，使用两个锁分别锁出队和入队，性能较高
 *
 * author:fanzhoukai
 * 2019/8/19 22:43
 */
public class MyLinkedBlockingQueue<E> {
	/*
	 * 本结构是“双锁队列”算法的变体。
	 * 入队列时，上锁putLock，且有一个关联的condition等待插入；出队列也一样。
	 * count（当前元素个数）属性，入队和出队都依赖此属性，为了避免同时上双锁，所以让它本身是AtomicInteger。
	 *
	 * 为了尽量少的上锁，使用了级联唤醒的操作（cascading notify）：
	 * 当一个put操作结束时，会唤醒一个阻塞中的take操作；如果唤醒后，刚好又有其他元素入队列，这个take操作又会唤醒其他的take操作。
	 * 对于take操作唤醒阻塞中的put线程也是一样。
	 * 移除元素（remove）、获取迭代器（iterators）这俩操作会同时获取两个锁。
	 */

	// 双链表节点
	static class Node<E> {
		E item;
		// 真实的下一个节点 / null表示这是最后一个节点 / 当前节点，meaning the successor is head.next TODO
		Node<E> next;
		Node(E x) { item = x; }
	}

	// 队列容量
	private final int capacity;

	// 当前元素个数
	private final AtomicInteger count = new AtomicInteger();

	// 虚拟头结点，item永远为空
	transient Node<E> head;

	// 链表尾节点，next永远为空
	private transient Node<E> last;

	// 取出元素时需要上的锁（take, pool...）
	private final ReentrantLock takeLock = new ReentrantLock();
	// 取出元素的等待队列
	private final Condition notEmpty = takeLock.newCondition();

	// 插入元素时需要上的锁（put, offer...）
	private final ReentrantLock putLock = new ReentrantLock();
	// 插入元素的等待队列
	private final Condition notFull = putLock.newCondition();


	// 元素入队列，若队列已满，无限阻塞
	public void put(E e) throws InterruptedException {
		if (e == null) throw new NullPointerException();
		// 预设-1，表示操作失败（入队、出队，下同）
		int c = -1;
		Node<E> node = new Node<E>(e);
		final ReentrantLock putLock = this.putLock;
		final AtomicInteger count = this.count;
		// 上put锁
		putLock.lockInterruptibly();
		try {
			// 队列已满，阻塞
			while (count.get() == capacity) {
				notFull.await();
			}
			// 队列未满/唤醒后，元素入队列，count值递增
			enqueue(node);
			c = count.getAndIncrement();
			// 若增加后仍然未满，则尝试唤醒一个阻塞中的take操作
			if (c + 1 < capacity)
				notFull.signal();
		} finally {
			// 解开put锁
			putLock.unlock();
		}
		// 若之前队列为空，由于刚刚新加了一个元素，则唤醒一个阻塞中的take操作
		if (c == 0)
			signalNotEmpty();
	}

	/**
	 * Inserts the specified element at the tail of this queue, waiting if
	 * necessary up to the specified wait time for space to become available.
	 *
	 * @return {@code true} if successful, or {@code false} if
	 *         the specified waiting time elapses before space is available
	 * @throws InterruptedException {@inheritDoc}
	 * @throws NullPointerException {@inheritDoc}
	 */
	public boolean offer(E e, long timeout, TimeUnit unit)
			throws InterruptedException {

		if (e == null) throw new NullPointerException();
		long nanos = unit.toNanos(timeout);
		int c = -1;
		final ReentrantLock putLock = this.putLock;
		final AtomicInteger count = this.count;
		putLock.lockInterruptibly();
		try {
			while (count.get() == capacity) {
				if (nanos <= 0)
					return false;
				nanos = notFull.awaitNanos(nanos);
			}
			enqueue(new Node<E>(e));
			c = count.getAndIncrement();
			if (c + 1 < capacity)
				notFull.signal();
		} finally {
			putLock.unlock();
		}
		if (c == 0)
			signalNotEmpty();
		return true;
	}

	/**
	 * Inserts the specified element at the tail of this queue if it is
	 * possible to do so immediately without exceeding the queue's capacity,
	 * returning {@code true} upon success and {@code false} if this queue
	 * is full.
	 * When using a capacity-restricted queue, this method is generally
	 * preferable to method {@link BlockingQueue#add add}, which can fail to
	 * insert an element only by throwing an exception.
	 *
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean offer(E e) {
		if (e == null) throw new NullPointerException();
		final AtomicInteger count = this.count;
		if (count.get() == capacity)
			return false;
		int c = -1;
		Node<E> node = new Node<E>(e);
		final ReentrantLock putLock = this.putLock;
		putLock.lock();
		try {
			if (count.get() < capacity) {
				enqueue(node);
				c = count.getAndIncrement();
				if (c + 1 < capacity)
					notFull.signal();
			}
		} finally {
			putLock.unlock();
		}
		if (c == 0)
			signalNotEmpty();
		return c >= 0;
	}

	public E take() throws InterruptedException {
		E x;
		int c = -1;
		final AtomicInteger count = this.count;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lockInterruptibly();
		try {
			while (count.get() == 0) {
				notEmpty.await();
			}
			x = dequeue();
			c = count.getAndDecrement();
			if (c > 1)
				notEmpty.signal();
		} finally {
			takeLock.unlock();
		}
		if (c == capacity)
			signalNotFull();
		return x;
	}

	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		E x = null;
		int c = -1;
		long nanos = unit.toNanos(timeout);
		final AtomicInteger count = this.count;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lockInterruptibly();
		try {
			while (count.get() == 0) {
				if (nanos <= 0)
					return null;
				nanos = notEmpty.awaitNanos(nanos);
			}
			x = dequeue();
			c = count.getAndDecrement();
			if (c > 1)
				notEmpty.signal();
		} finally {
			takeLock.unlock();
		}
		if (c == capacity)
			signalNotFull();
		return x;
	}

	public E poll() {
		final AtomicInteger count = this.count;
		if (count.get() == 0)
			return null;
		E x = null;
		int c = -1;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lock();
		try {
			if (count.get() > 0) {
				x = dequeue();
				c = count.getAndDecrement();
				if (c > 1)
					notEmpty.signal();
			}
		} finally {
			takeLock.unlock();
		}
		if (c == capacity)
			signalNotFull();
		return x;
	}

	public E peek() {
		if (count.get() == 0)
			return null;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lock();
		try {
			Node<E> first = head.next;
			if (first == null)
				return null;
			else
				return first.item;
		} finally {
			takeLock.unlock();
		}
	}

	/**
	 * Unlinks interior Node p with predecessor trail.
	 */
	void unlink(Node<E> p, Node<E> trail) {
		// assert isFullyLocked();
		// p.next is not changed, to allow iterators that are
		// traversing p to maintain their weak-consistency guarantee.
		p.item = null;
		trail.next = p.next;
		if (last == p)
			last = trail;
		if (count.getAndDecrement() == capacity)
			notFull.signal();
	}


	/**
	 * Signals a waiting take. Called only from put/offer (which do not
	 * otherwise ordinarily lock takeLock.)
	 */
	private void signalNotEmpty() {
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lock();
		try {
			notEmpty.signal();
		} finally {
			takeLock.unlock();
		}
	}

	/**
	 * Signals a waiting put. Called only from take/poll.
	 */
	private void signalNotFull() {
		final ReentrantLock putLock = this.putLock;
		putLock.lock();
		try {
			notFull.signal();
		} finally {
			putLock.unlock();
		}
	}

	/**
	 * Links node at end of queue.
	 *
	 * @param node the node
	 */
	private void enqueue(Node<E> node) {
		// assert putLock.isHeldByCurrentThread();
		// assert last.next == null;
		last = last.next = node;
	}

	/**
	 * Removes a node from head of queue.
	 *
	 * @return the node
	 */
	private E dequeue() {
		// assert takeLock.isHeldByCurrentThread();
		// assert head.item == null;
		Node<E> h = head;
		Node<E> first = h.next;
		h.next = h; // help GC
		head = first;
		E x = first.item;
		first.item = null;
		return x;
	}

	/**
	 * Locks to prevent both puts and takes.
	 */
	void fullyLock() {
		putLock.lock();
		takeLock.lock();
	}

	/**
	 * Unlocks to allow both puts and takes.
	 */
	void fullyUnlock() {
		takeLock.unlock();
		putLock.unlock();
	}

//     /**
//      * Tells whether both locks are held by current thread.
//      */
//     boolean isFullyLocked() {
//         return (putLock.isHeldByCurrentThread() &&
//                 takeLock.isHeldByCurrentThread());
//     }

	/**
	 * Creates a {@code LinkedBlockingQueue} with a capacity of
	 * {@link Integer#MAX_VALUE}.
	 */
	public MyLinkedBlockingQueue() {
		this(Integer.MAX_VALUE);
	}

	/**
	 * Creates a {@code LinkedBlockingQueue} with the given (fixed) capacity.
	 *
	 * @param capacity the capacity of this queue
	 * @throws IllegalArgumentException if {@code capacity} is not greater
	 *         than zero
	 */
	public MyLinkedBlockingQueue(int capacity) {
		if (capacity <= 0) throw new IllegalArgumentException();
		this.capacity = capacity;
		last = head = new Node<E>(null);
	}

	/**
	 * Creates a {@code LinkedBlockingQueue} with a capacity of
	 * {@link Integer#MAX_VALUE}, initially containing the elements of the
	 * given collection,
	 * added in traversal order of the collection's iterator.
	 *
	 * @param c the collection of elements to initially contain
	 * @throws NullPointerException if the specified collection or any
	 *         of its elements are null
	 */
	public MyLinkedBlockingQueue(Collection<? extends E> c) {
		this(Integer.MAX_VALUE);
		final ReentrantLock putLock = this.putLock;
		putLock.lock(); // Never contended, but necessary for visibility
		try {
			int n = 0;
			for (E e : c) {
				if (e == null)
					throw new NullPointerException();
				if (n == capacity)
					throw new IllegalStateException("Queue full");
				enqueue(new Node<E>(e));
				++n;
			}
			count.set(n);
		} finally {
			putLock.unlock();
		}
	}

	// this doc comment is overridden to remove the reference to collections
	// greater in size than Integer.MAX_VALUE
	/**
	 * Returns the number of elements in this queue.
	 *
	 * @return the number of elements in this queue
	 */
	public int size() {
		return count.get();
	}

	// this doc comment is a modified copy of the inherited doc comment,
	// without the reference to unlimited queues.
	/**
	 * Returns the number of additional elements that this queue can ideally
	 * (in the absence of memory or resource constraints) accept without
	 * blocking. This is always equal to the initial capacity of this queue
	 * less the current {@code size} of this queue.
	 *
	 * <p>Note that you <em>cannot</em> always tell if an attempt to insert
	 * an element will succeed by inspecting {@code remainingCapacity}
	 * because it may be the case that another thread is about to
	 * insert or remove an element.
	 */
	public int remainingCapacity() {
		return capacity - count.get();
	}

	/**
	 * Removes a single instance of the specified element from this queue,
	 * if it is present.  More formally, removes an element {@code e} such
	 * that {@code o.equals(e)}, if this queue contains one or more such
	 * elements.
	 * Returns {@code true} if this queue contained the specified element
	 * (or equivalently, if this queue changed as a result of the call).
	 *
	 * @param o element to be removed from this queue, if present
	 * @return {@code true} if this queue changed as a result of the call
	 */
	public boolean remove(Object o) {
		if (o == null) return false;
		fullyLock();
		try {
			for (Node<E> trail = head, p = trail.next;
			     p != null;
			     trail = p, p = p.next) {
				if (o.equals(p.item)) {
					unlink(p, trail);
					return true;
				}
			}
			return false;
		} finally {
			fullyUnlock();
		}
	}

	/**
	 * Returns {@code true} if this queue contains the specified element.
	 * More formally, returns {@code true} if and only if this queue contains
	 * at least one element {@code e} such that {@code o.equals(e)}.
	 *
	 * @param o object to be checked for containment in this queue
	 * @return {@code true} if this queue contains the specified element
	 */
	public boolean contains(Object o) {
		if (o == null) return false;
		fullyLock();
		try {
			for (Node<E> p = head.next; p != null; p = p.next)
				if (o.equals(p.item))
					return true;
			return false;
		} finally {
			fullyUnlock();
		}
	}

	/**
	 * Returns an array containing all of the elements in this queue, in
	 * proper sequence.
	 *
	 * <p>The returned array will be "safe" in that no references to it are
	 * maintained by this queue.  (In other words, this method must allocate
	 * a new array).  The caller is thus free to modify the returned array.
	 *
	 * <p>This method acts as bridge between array-based and collection-based
	 * APIs.
	 *
	 * @return an array containing all of the elements in this queue
	 */
	public Object[] toArray() {
		fullyLock();
		try {
			int size = count.get();
			Object[] a = new Object[size];
			int k = 0;
			for (Node<E> p = head.next; p != null; p = p.next)
				a[k++] = p.item;
			return a;
		} finally {
			fullyUnlock();
		}
	}

	/**
	 * Returns an array containing all of the elements in this queue, in
	 * proper sequence; the runtime type of the returned array is that of
	 * the specified array.  If the queue fits in the specified array, it
	 * is returned therein.  Otherwise, a new array is allocated with the
	 * runtime type of the specified array and the size of this queue.
	 *
	 * <p>If this queue fits in the specified array with room to spare
	 * (i.e., the array has more elements than this queue), the element in
	 * the array immediately following the end of the queue is set to
	 * {@code null}.
	 *
	 * <p>Like the {@link #toArray()} method, this method acts as bridge between
	 * array-based and collection-based APIs.  Further, this method allows
	 * precise control over the runtime type of the output array, and may,
	 * under certain circumstances, be used to save allocation costs.
	 *
	 * <p>Suppose {@code x} is a queue known to contain only strings.
	 * The following code can be used to dump the queue into a newly
	 * allocated array of {@code String}:
	 *
	 *  <pre> {@code String[] y = x.toArray(new String[0]);}</pre>
	 *
	 * Note that {@code toArray(new Object[0])} is identical in function to
	 * {@code toArray()}.
	 *
	 * @param a the array into which the elements of the queue are to
	 *          be stored, if it is big enough; otherwise, a new array of the
	 *          same runtime type is allocated for this purpose
	 * @return an array containing all of the elements in this queue
	 * @throws ArrayStoreException if the runtime type of the specified array
	 *         is not a supertype of the runtime type of every element in
	 *         this queue
	 * @throws NullPointerException if the specified array is null
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) {
		fullyLock();
		try {
			int size = count.get();
			if (a.length < size)
				a = (T[])java.lang.reflect.Array.newInstance
						(a.getClass().getComponentType(), size);

			int k = 0;
			for (Node<E> p = head.next; p != null; p = p.next)
				a[k++] = (T)p.item;
			if (a.length > k)
				a[k] = null;
			return a;
		} finally {
			fullyUnlock();
		}
	}

	public String toString() {
		fullyLock();
		try {
			Node<E> p = head.next;
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
			fullyUnlock();
		}
	}

	/**
	 * Atomically removes all of the elements from this queue.
	 * The queue will be empty after this call returns.
	 */
	public void clear() {
		fullyLock();
		try {
			for (Node<E> p, h = head; (p = h.next) != null; h = p) {
				h.next = h;
				p.item = null;
			}
			head = last;
			// assert head.item == null && head.next == null;
			if (count.getAndSet(0) == capacity)
				notFull.signal();
		} finally {
			fullyUnlock();
		}
	}

	/**
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException            {@inheritDoc}
	 * @throws NullPointerException          {@inheritDoc}
	 * @throws IllegalArgumentException      {@inheritDoc}
	 */
	public int drainTo(Collection<? super E> c) {
		return drainTo(c, Integer.MAX_VALUE);
	}

	/**
	 * @throws UnsupportedOperationException {@inheritDoc}
	 * @throws ClassCastException            {@inheritDoc}
	 * @throws NullPointerException          {@inheritDoc}
	 * @throws IllegalArgumentException      {@inheritDoc}
	 */
	public int drainTo(Collection<? super E> c, int maxElements) {
		if (c == null)
			throw new NullPointerException();
		if (c == this)
			throw new IllegalArgumentException();
		if (maxElements <= 0)
			return 0;
		boolean signalNotFull = false;
		final ReentrantLock takeLock = this.takeLock;
		takeLock.lock();
		try {
			int n = Math.min(maxElements, count.get());
			// count.get provides visibility to first n Nodes
			Node<E> h = head;
			int i = 0;
			try {
				while (i < n) {
					Node<E> p = h.next;
					c.add(p.item);
					p.item = null;
					h.next = h;
					h = p;
					++i;
				}
				return n;
			} finally {
				// Restore invariants even if c.add() threw
				if (i > 0) {
					// assert h.item == null;
					head = h;
					signalNotFull = (count.getAndAdd(-i) == capacity);
				}
			}
		} finally {
			takeLock.unlock();
			if (signalNotFull)
				signalNotFull();
		}
	}

	// TODO 迭代器相关暂时不看
	/**
	 * Returns an iterator over the elements in this queue in proper sequence.
	 * The elements will be returned in order from first (head) to last (tail).
	 *
	 * <p>The returned iterator is
	 * <a href="package-summary.html#Weakly"><i>weakly consistent</i></a>.
	 *
	 * @return an iterator over the elements in this queue in proper sequence
	 */
	public Iterator<E> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<E> {
		/*
		 * Basic weakly-consistent iterator.  At all times hold the next
		 * item to hand out so that if hasNext() reports true, we will
		 * still have it to return even if lost race with a take etc.
		 */

		private Node<E> current;
		private Node<E> lastRet;
		private E currentElement;

		Itr() {
			fullyLock();
			try {
				current = head.next;
				if (current != null)
					currentElement = current.item;
			} finally {
				fullyUnlock();
			}
		}

		public boolean hasNext() {
			return current != null;
		}

		/**
		 * Returns the next live successor of p, or null if no such.
		 *
		 * Unlike other traversal methods, iterators need to handle both:
		 * - dequeued nodes (p.next == p)
		 * - (possibly multiple) interior removed nodes (p.item == null)
		 */
		private Node<E> nextNode(Node<E> p) {
			for (;;) {
				Node<E> s = p.next;
				if (s == p)
					return head.next;
				if (s == null || s.item != null)
					return s;
				p = s;
			}
		}

		public E next() {
			fullyLock();
			try {
				if (current == null)
					throw new NoSuchElementException();
				E x = currentElement;
				lastRet = current;
				current = nextNode(current);
				currentElement = (current == null) ? null : current.item;
				return x;
			} finally {
				fullyUnlock();
			}
		}

		public void remove() {
			if (lastRet == null)
				throw new IllegalStateException();
			fullyLock();
			try {
				Node<E> node = lastRet;
				lastRet = null;
				for (Node<E> trail = head, p = trail.next;
				     p != null;
				     trail = p, p = p.next) {
					if (p == node) {
						unlink(p, trail);
						break;
					}
				}
			} finally {
				fullyUnlock();
			}
		}
	}
}
