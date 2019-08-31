package com.learning.project.fzk.blockingQueue.use;

import com.learning.project.fzk.blockingQueue.srcCode.MyDelayQueue;
import org.junit.Test;

import java.util.Date;

/**
 * 延迟队列的实际使用场景
 * <p>
 * 利用延迟队列，来实现任务的定时开启与关闭
 * <p>
 * author:fanzhoukai
 * 2019/8/31 15:15
 */
public class TestDelayQueue {
	// 等待中的任务队列
	private static MyDelayQueue<TaskWaitingRun> waitingTaskQueue = new MyDelayQueue();
	// 运行中的任务队列
	private static MyDelayQueue<TaskRunning> runningTaskQueue = new MyDelayQueue();

	static {
		new Thread(() -> {
			try {
				while (true) {
					TaskWaitingRun task = waitingTaskQueue.take();
					if (task != null) {
						// 转为运行中的任务，放入运行中的队列
						System.out.println(DateUtil.dateToString(new Date()) + " 任务【" + task.name + "】进入运行状态");
						runningTaskQueue.offer(new TaskRunning(task.name, task.startTime, task.endTime));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				while (true) {
					TaskRunning task = runningTaskQueue.take();
					if (task != null) {
						// 转为运行中的任务，放入运行中的队列
						System.out.println(DateUtil.dateToString(new Date()) + " 任务【" + task.name + "】运行结束");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	/**
	 * 添加一个任务
	 */
	public static void addTask(Task task) {
		waitingTaskQueue.offer(new TaskWaitingRun(task.name, task.startTime, task.endTime));
	}


	@Test
	public void testTask() throws InterruptedException {
		addTask(new Task("任务1", "2019-08-31 16:17:00", "2019-08-31 16:17:05"));
		addTask(new Task("任务2", "2019-08-31 16:17:03", "2019-08-31 16:17:08"));
		Thread.sleep(100000);
	}
}
