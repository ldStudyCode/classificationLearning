package com.learning.project.fzk.multiThread.cache;

/**
 * 缓存工具类接口
 *
 * @param <K>
 * @param <V>
 */
public interface Cache<K, V> {
    V getResult(K key);
}
