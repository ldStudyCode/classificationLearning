package com.learning.project.fzk.juc.lock;

/**
 * 精简版ReentrantLock，仅用于实现基础的互斥锁功能
 */

import com.learning.project.fzk.juc.aqs.MyAQS;
import com.learning.project.fzk.juc.aqs.MySimpleAQS;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MySimpleReentrantLock {
    // 支持所有实现的同步器
    private final Sync sync;

    /**
     * 互斥锁的同步控制基础，子类会实现公平、非公平两个版本。
     * 使用AQS的state来表示一个线程获取锁对象的次数
     */
    abstract static class Sync extends MySimpleAQS {
        /**
         * Performs {@link Lock#lock}. The main reason for subclassing
         * is to allow fast path for nonfair version.
         */
        abstract void lock();

        /**
         * Performs non-fair tryLock.  tryAcquire is implemented in
         * subclasses, but both need nonfair try for trylock method.
         */
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }

        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }

        protected final boolean isHeldExclusively() {
            // While we must in general read state before owner,
            // we don't need to do so to check if current thread is owner
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        // Methods relayed from outer class

        final Thread getOwner() {
            return getState() == 0 ? null : getExclusiveOwnerThread();
        }

        final int getHoldCount() {
            return isHeldExclusively() ? getState() : 0;
        }

        final boolean isLocked() {
            return getState() != 0;
        }

        /**
         * Reconstitutes the instance from a stream (that is, deserializes it).
         */
        private void readObject(java.io.ObjectInputStream s)
                throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    /**
     * Sync object for non-fair locks
     */
    static final class NonfairSync extends Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        /**
         * Performs lock.  Try immediate barge, backing up to normal
         * acquire on failure.
         */
        final void lock() {
            if (compareAndSetState(0, 1))
                setExclusiveOwnerThread(Thread.currentThread());
            else
                acquire(1);
        }

        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }
    }

    /**
     * Sync object for fair locks
     */
    static final class FairSync extends Sync {

        final void lock() {
            acquire(1);
        }

        /**
         * 公平地尝试获取锁
         * 不保证成功，除非递归调用、没有其他等待的线程，或本身就是第一个线程
         */
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            // 同步状态，ReentrantLock使用它来表示线程上锁次数
            int c = getState();
            if (c == 0) {
                // 锁计数器=0，表示锁未被任何线程获取到
                // 若没有其他线程等待的时间更长（注意：这是保证公平的条件），那就可以CAS地将锁计数器从0变为1。
                // 若CAS成功，可将当前线程记录下来；失败，返回false
                if (!hasQueuedPredecessors() && compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                // 锁计数器不为0，那么只有在线程重入时才能获取锁
                int nextc = c + acquires;
                if (nextc < 0) {
                    throw new Error("Maximum lock count exceeded");
                }
                // 这里没有用CAS，是因为可重入情况一定在单线程环境下串行执行，无需CAS
                setState(nextc);
                return true;
            }
            return false;
        }
    }

    /**
     * 创建互斥锁实例，默认非公平
     */
    public MySimpleReentrantLock() {
        sync = new NonfairSync();
    }

    /**
     * 创建互斥锁实例，指定公平/非公平
     */
    public MySimpleReentrantLock(boolean fair) {
        sync = fair ? new FairSync() : new NonfairSync();
    }

    /**
     * 获取锁对象
     * 若锁对象没有被其他线程持有，则获取成功，立即返回，并将锁计数器设置为1。
     * 若当前线程已经持有这个锁，那么锁计数器递增1，并立即返回。
     * 若锁对象被其他线程持有，那么出于线程调度的目的，当前线程将休眠，直到获取锁为止，
     * 此时锁计数器设置为1。
     */
    public void lock() {
        sync.lock();
    }

    /**
     * 释放锁对象
     * 若当前线程正在持有锁对象，那么锁计数器减1。减至0时锁被释放掉。
     * 若当前线程没有持有锁对象（包括unlock次数大于lock次数的情况），将抛出IllegalMonitorStateException
     */
    public void unlock() {
        sync.release(1);
    }

    /**
     * 获取当前lock对象的一个Condition实例
     *
     * 返回的Condition实例支持与Object类中wait()、notify()、notifyAll()相同的操作。
     * 若锁对象没有被当前线程持有，就调用了await()或signal()方法，将抛出IllegalMonitorStateException异常；
     * Condition#await()方法被调用时，锁会被释放，且在方法返回前，锁对象会被重新获取到，而锁计数器与调用await()之前没有变化。
     * 若一个线程在等待锁时被中断了，那么将抛出InterruptedException，且线程的中断状态值将被清空。
     * 等待线程会以FIFO顺序地被唤醒。
     * await()方法返回，重新获取锁的顺序，默认与线程初始获取锁的顺序相同，但对于公平锁来说，等待时间最长的线程将获取到锁。
     */
    public Condition newCondition() {
        return sync.newCondition();
    }
}