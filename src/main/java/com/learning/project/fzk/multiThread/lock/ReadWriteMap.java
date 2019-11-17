package com.learning.project.fzk.multiThread.lock;

import com.learning.project.fzk.juc.lock.MyReentrantReadWriteLock;
import com.learning.project.fzk.juc.lock.MySimpleLock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * 一个支持读写锁的map
 * 利用ReentrantReadWriteLock，包装一个支持读写锁的Map结构
 */
public class ReadWriteMap<K, V> {

	private final Map<K, V> map;

	private final MyReentrantReadWriteLock lock = new MyReentrantReadWriteLock();
	private final MySimpleLock readLock = lock.readLock();
	private final MySimpleLock writeLock = lock.writeLock();

	public ReadWriteMap() {
		this.map = new HashMap();
	}

	public ReadWriteMap(Map map) {
		if (map == null)
			map = new HashMap();
		this.map = map;
	}

	public V put(K key, V value) {
		writeLock.lock();
		try {
			return map.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	public V get(Object key) {
		readLock.lock();
		try {
			return map.get(key);
		} finally {
			readLock.unlock();
		}
	}

}
