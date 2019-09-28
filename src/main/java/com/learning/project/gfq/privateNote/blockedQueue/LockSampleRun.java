package com.learning.project.gfq.privateNote.blockedQueue;

import com.learning.project.fzk.lock.LockSample;

public class LockSampleRun {

    public static void main(String[] args) throws Exception {
        LockSample lockSample = new LockSample();
        /*
        synchronized
            阻塞后随机唤醒，即非公平唤醒
            一个池子大家塞在一起
        */
        lockSample.testSynchronized();
        /*
        reentrantLock
            按阻塞的先后顺序进行唤醒
            可以分多个组各自分别阻塞
        */
        lockSample.testReentrantLock();
    }
}
