package com.learning.project.fzk.multiThread.cache;

/**
 * 计算函数，传入Cache中进行计算
 *
 * @param <K> key类型
 * @param <V> value类型
 */
public interface Computable<K,V> {
    V compute(K key) throws Exception;
}
