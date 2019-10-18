package com.learning.project.fzk.multiThread.cache;

/**
 * 缓存工具类接口
 *
 * @see Cache1：使用Future占位符实现，有多线程同时计算一个key的浪费问题
 * @see Cache2：使用ConcurrentHashMap#putIfAbsent实现
 * @see Cache3：使用ConcurrentHashMap#computeIfAbsent实现
 *
 * @param <K> key类型
 * @param <V> value类型
 *
 */
public interface Cache<K, V> {
    V getResult(K key);
}
