package com.learning.project.fzk.juc.aqs;

/**
 * @see java.util.concurrent.locks.AbstractOwnableSynchronizer
 */
/**
 * A synchronizer that may be exclusively owned by a thread.  This
 * class provides a basis for creating locks and related synchronizers
 * that may entail a notion of ownership.  The
 * {@code AbstractOwnableSynchronizer} class itself does not manage or
 * use this information. However, subclasses and tools may use
 * appropriately maintained values to help control and monitor access
 * and provide diagnostics.
 *
 * @since 1.6
 * @author Doug Lea
 */
public class MyAbstractOwnableSynchronizer {
    /**
     * Empty constructor for use by subclasses.
     */
    protected MyAbstractOwnableSynchronizer() { }

    // 排他锁的当前持有线程
    private transient Thread exclusiveOwnerThread;

    /**
     * 设置当前能排他访问的线程。可为null，表示没有线程获取到锁。
     * 该方法不强制访问同步性、volatile字段
     */
    protected final void setExclusiveOwnerThread(Thread thread) {
        exclusiveOwnerThread = thread;
    }

    /**
     * Returns the thread last set by {@code setExclusiveOwnerThread},
     * or {@code null} if never set.  This method does not otherwise
     * impose any synchronization or {@code volatile} field accesses.
     * @return the owner thread
     */
    protected final Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }
}
