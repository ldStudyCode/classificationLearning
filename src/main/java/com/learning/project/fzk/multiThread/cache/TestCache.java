package com.learning.project.fzk.multiThread.cache;

import org.junit.Test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类测试程序
 */
public class TestCache {

    /**
     * 测试入口
     */
    @Test
    public void test() throws Exception {
        // 初始化缓存结构时，指定转换函数
        Cache<String, String> cache = new Cache3<String, String>(new Computer());

        // 并行运行线程数
        final int threadNum = 100;
        ThreadPoolExecutor pool = new ThreadPoolExecutor(threadNum, threadNum, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        // 线程栅栏：为了强迫所有线程处于同一起跑线，然后同时执行getResult。
        // +1是主线程的1
        CyclicBarrier barrier = new CyclicBarrier(threadNum + 1);

        for (int i = 0; i < threadNum; i++) {
            pool.execute(() -> {
                        try {
                            barrier.await(); // 表明当前线程已就绪，随时可以开始执行
                            String a = cache.getResult("key");
                            System.out.println(a);
                            barrier.await(); // 表明当前线程已执行完
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        barrier.await(); // 第一轮等待：等待所有线程就绪
        barrier.await(); // 第二轮等待：等待所有线程执行结束
        System.out.println("执行结束");
    }

    /**
     * 计算程序，模拟高延迟+随机报错
     */
    static class Computer implements Computable {

        @Override
        public Object compute(Object key) throws Exception {
            System.out.println("计算中...");
            Thread.sleep(500); // 模拟耗时计算
            if ((System.currentTimeMillis() & 0b11) != 0) { // 模拟随机报错，报错率75%
                System.err.println("compute error");
                throw new RuntimeException("compute error");
            }
            return key + "_result";
        }
    }
}
