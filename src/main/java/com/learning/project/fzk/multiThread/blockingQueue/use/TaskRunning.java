package com.learning.project.fzk.multiThread.blockingQueue.use;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 已开始的任务
 * <p>
 * author:fanzhoukai
 * 2019/8/31 15:29
 */
public class TaskRunning extends Task implements Delayed {
	public TaskRunning() {
	}

	public TaskRunning(String name, String startTime, String endTime) throws Exception {
		super(name, startTime, endTime);
	}

	/**
	 * 获取任务剩余存活时间
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		long now = System.currentTimeMillis();
		try {
			return DateUtil.stringToLong(super.endTime) - now;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 与其他任务比较剩余时间，用于构造优先队列
	 */
	@Override
	public int compareTo(Delayed o) {
		return (int)(getDelay(null) - o.getDelay(null));
		/*if (o instanceof TaskRunning) {
			TaskRunning o1 = (TaskRunning) o;
			try {
				return (int) (DateUtil.stringToLong(o1.endTime) - DateUtil.stringToLong(this.endTime));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // TODO*/
	}
}
