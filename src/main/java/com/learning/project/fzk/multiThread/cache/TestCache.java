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

        final int threadNum = 100;
        // 并行运行
        ThreadPoolExecutor pool = new ThreadPoolExecutor(threadNum, threadNum, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        // 线程栅栏：为了强迫所有线程处于同一起跑线，然后同时执行getResult。
        // +1是主线程的1
        CyclicBarrier barrier = new CyclicBarrier(threadNum + 1);

        for (int i = 0; i < 100; i++) {
            pool.execute(() -> {
                        try {
                            barrier.await(); // 表明当前线程已就绪，随时可以开始执行
                            String a = cache.getResult("key");
                            System.out.println(a);
                            barrier.await(); // 表明当前线程已执行完，结果已存入共享累加器中
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        barrier.await(); // 等待所有线程就绪
        barrier.await(); // 等待所有线程执行结束
        System.out.println("执行结束");
    }

    /**
     * 计算程序，模拟高延迟+随机报错
     */
    static class Computer implements Computable {

        @Override
        public Object compute(Object key) throws Exception {
            System.out.println("计算中...");
            if ((System.currentTimeMillis() & 3) != 0) { // 模拟随机报错，报错率75%
                System.err.println("compute error");
                throw new RuntimeException("compute error");
            }
            Thread.sleep(100); // 模拟耗时计算
            return key + "_result";
        }
    }
}
