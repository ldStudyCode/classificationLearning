package com.learning.project.fzk.lock;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 一个支持读写锁的map
 * 利用ReentrantReadWriteLock，包装一个支持读写锁的Map结构
 */
public class ReadWriteMap<K, V> {

	private final Map<K, V> map;

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();

	public ReadWriteMap(Map map) {
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
