package com.learning.project.fzk.multiThread.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * 本地缓存工具类
 * 使用ConcurrentHashMap#putIfAbsent实现
 *
 * 类似于双重检查加锁的单例模式，使用类似的思路保证了计算过程和结果的单例
 * 第一次非原子性的检查，保证了不会每次都创建新对象；第二次原子性检查，保证只有一个线程才有机会put元素；
 * 最后一次检查，为了保证只有一个线程才能执行计算过程
 *
 * 示例：
 * 100个线程同时获取同一个key的结果；
 * 10个线程第一次判断通过，于是创建了10个Future占位符；剩下90个都在future.get()方法中阻塞了；
 * 10个线程同时对自己的Future占位符执行putIfAbsent操作；
 * 只有1个线程执行成功，并返回null，于是自己执行；
 * 其他9个线程执行失败，并返回上面成功的线程创建的Future占位符，同样在future.get()方法中阻塞；
 * 若唯一成功的Future执行失败，则阻塞中的99个线程都会捕获异常，并进行重试；
 * 直到某一次的Future执行成功，则唤醒99个线程获取结果。
 *
 * 最终运行结果为：
 *   计算中...
 *   compute error
 *   计算中...
 *   compute error
 *   计算中...
 *   key_result
 *   key_result
 *   key_result
 *   key_result
 *   ...
 * 即，只要一次计算成功，后面都不会执行重复的计算
 */
public class Cache2<K, V> implements Cache<K, V> {

    private final ConcurrentHashMap<K, Future<V>> map = new ConcurrentHashMap();
    private final Computable<K, V> computable;

    public Cache2(Computable<K, V> computable) {
        this.computable = computable;
    }

    public V getResult(K key) {
        while (true) { // 加一层循环，处理计算失败的情况
            Future<V> future = map.get(key);
            // 未命中，第一次检查
            if (future == null) {
                FutureTask<V> futureTask = new FutureTask<>(() -> computable.compute(key));
                // 第二次原子性检查，并记录是否put成功：若返回值为空，说明成功抢到锁并put进去；若返回值非空，说明已经有其他线程抢先put进去了
                future = map.putIfAbsent(key, futureTask);
                if (future == null) { // 只有是当前线程put成功，才开始计算，否则等着其他线程算完就行了
                    future = futureTask;
                    futureTask.run(); // 开始计算
                }
            }
            // 此时，这里的future可能是当前线程put进去的，也可能是当前线程put失败，返回了其他线程的。
            // 从中拿到结果（阻塞一会/立即拿到）后返回即可
            try {
                return future.get();
            } catch (Throwable e) {
                // 若compute抛出异常，移出future重新计算
                map.remove(key, future);
            }
        }
    }
}

