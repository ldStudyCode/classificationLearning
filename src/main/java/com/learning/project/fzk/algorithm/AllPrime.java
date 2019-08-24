package com.learning.project.fzk.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 求2-20000的所有素数
 * <p>
 * author:fanzhoukai
 * 2019/7/28 17:45
 */
public class AllPrime {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		List<Integer> allPrime = getAllPrime_2(20000);
		System.out.println("总数：" + allPrime.size());
		for (Integer integer : allPrime) {
			System.out.print(integer + "\t");
		}
	}

	// 多线程版1：使用CountDownLatch等待结果
	private static List<Integer> getAllPrime_1(int limit) throws InterruptedException {
		// 注意！多线程同时插入，必须使用线程安全的list结构。坑！
		final List<Integer> list = new CopyOnWriteArrayList<>();

		// 线程池固定线程数量
		final int poolSize = 5;
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);
		// 线程倒数计数器
		CountDownLatch latch = new CountDownLatch(poolSize);

		for (int i = 2; i < 2 + poolSize; i++) {
			// 每个线程的初始值，以固定线程数量为步长进行递增
			final int initValue = i;

			pool.execute(() -> {
				for (int j = initValue; j <= limit; j += poolSize) {
					if (isPrime(j)) {
						list.add(j);
					}
				}
				latch.countDown();
			});
		}
		// 阻塞住，等所有线程都执行countDown()了才继续执行
		latch.await();
		// 关闭线程池
		pool.shutdown();
		return list;
	}

	// 多线程版2：使用Future等待结果
	private static List<Integer> getAllPrime_2(int limit) throws InterruptedException, ExecutionException {
		final List<Integer> list = new CopyOnWriteArrayList<>();

		// 线程池固定线程数量
		final int poolSize = 5;
		ExecutorService pool = Executors.newFixedThreadPool(poolSize);

		for (int i = 2; i < 2 + poolSize; i++) {
			// 每个线程的初始值，以固定线程数量为步长进行递增
			final int initValue = i;

			Future<List> submit = pool.submit(() -> {
				List l = new ArrayList();
				for (int j = initValue; j <= limit; j += poolSize) {
					if (isPrime(j)) {
						l.add(j);
					}
				}
				return l;
			});
			list.addAll(submit.get());
		}
		// 关闭线程池
		pool.shutdown();
		return list;
	}

	// 单线程版
	private static List<Integer> getAllPrime_backup(int limit) {
		List<Integer> list = new ArrayList<>();
		for (int i = 2; i <= limit; i++) {
			if (isPrime(i)) {
				list.add(i);
			}
		}
		return list;
	}


	// 是否是质数
	private static boolean isPrime(int i) {
		double sqrt = Math.sqrt(i);
		for (int j = 2; j <= (int) sqrt; j++) {
			if (i % j == 0) {
				return false;
			}
		}
		return true;
	}
}
