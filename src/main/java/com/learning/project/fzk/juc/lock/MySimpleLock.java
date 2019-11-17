/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */

package com.learning.project.fzk.juc.lock;

import java.util.concurrent.locks.Condition;

public interface MySimpleLock {

	/**
	 * Acquires the lock.
	 *
	 * <p>If the lock is not available then the current thread becomes
	 * disabled for thread scheduling purposes and lies dormant until the
	 * lock has been acquired.
	 *
	 * <p><b>Implementation Considerations</b>
	 *
	 * <p>A {@code Lock} implementation may be able to detect erroneous use
	 * of the lock, such as an invocation that would cause deadlock, and
	 * may throw an (unchecked) exception in such circumstances.  The
	 * circumstances and the exception type must be documented by that
	 * {@code Lock} implementation.
	 */
	void lock();

	/**
	 * Releases the lock.
	 *
	 * <p><b>Implementation Considerations</b>
	 *
	 * <p>A {@code Lock} implementation will usually impose
	 * restrictions on which thread can release a lock (typically only the
	 * holder of the lock can release it) and may throw
	 * an (unchecked) exception if the restriction is violated.
	 * Any restrictions and the exception
	 * type must be documented by that {@code Lock} implementation.
	 */
	void unlock();

	/**
	 * Returns a new {@link Condition} instance that is bound to this
	 * {@code Lock} instance.
	 *
	 * <p>Before waiting on the condition the lock must be held by the
	 * current thread.
	 * A call to {@link Condition#await()} will atomically release the lock
	 * before waiting and re-acquire the lock before the wait returns.
	 *
	 * <p><b>Implementation Considerations</b>
	 *
	 * <p>The exact operation of the {@link Condition} instance depends on
	 * the {@code Lock} implementation and must be documented by that
	 * implementation.
	 *
	 * @return A new {@link Condition} instance for this {@code Lock} instance
	 * @throws UnsupportedOperationException if this {@code Lock}
	 *                                       implementation does not support conditions
	 */
	Condition newCondition();
}
