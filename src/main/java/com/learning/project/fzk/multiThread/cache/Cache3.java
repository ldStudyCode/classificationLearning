package com.learning.project.fzk.multiThread.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.function.Function;

/**
 * 本地缓存工具类
 * 使用ConcurrentHashMap#computeIfAbsent实现
 * <p>
 * 与Cache2的唯一区别是，computeIfAbsent的入参Function的函数apply，不支持抛出异常，因此循环计算的逻辑要移到map里面去
 */
public class Cache3<K, V> implements Cache<K, V> {

    private final ConcurrentHashMap<K, V> map = new ConcurrentHashMap();
    private final Computable<K, V> computable;

    public Cache3(Computable<K, V> computable) {
        this.computable = computable;
    }

    public V getResult(K key) {
        while (true) { // 加一层循环，处理计算失败的情况
            V value = map.get(key);
            // 未命中，第一次检查
            if (value == null) {
                // 第二次原子性检查，并记录是否put成功：若返回值为空，说明成功抢到锁并put进去；若返回值非空，说明已经有其他线程抢先put进去了
                value = map.computeIfAbsent(key, k -> {
                    while (true) { // 处理计算失败的情况
                        try {
                            return computable.compute(key);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            // 此时，value一定已经计算完成
            return value;
        }
    }
}

