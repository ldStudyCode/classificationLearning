package com.learning.project.fzk.multiThread.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 本地缓存工具类
 * 使用Future占位符实现，有CPU浪费问题
 */
public class Cache1<K, V> implements Cache<K, V> {
    private final ConcurrentHashMap<K, Future<V>> map = new ConcurrentHashMap();
    private final Computable<K, V> computable;

    public Cache1(Computable<K, V> computable) {
        this.computable = computable;
    }

    public V getResult(K key) {
        while (true) { // 加一层循环，处理计算失败的情况
            Future<V> future = map.get(key);
            // 未命中，则向缓存中插入Future占位符，并开始计算
            if (future == null) {
                FutureTask<V> futureTask = new FutureTask<>(() -> computable.compute(key));
                future = futureTask;
                map.put(key, futureTask);
                futureTask.run(); // 开始计算
            }
            // 此时，保证要么当前线程正在计算中，要么其他线程已经计算结束
            // 从中拿到结果（阻塞一会/立即拿到）后返回
            try {
                return future.get();
            } catch (Throwable e) {
                map.remove(key, future);
            }
        }
    }
}

