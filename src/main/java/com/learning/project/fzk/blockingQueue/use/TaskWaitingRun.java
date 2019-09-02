package com.learning.project.fzk.blockingQueue.use;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 未开始的任务
 * <p>
 * author:fanzhoukai
 * 2019/8/31 15:29
 */
public class TaskWaitingRun extends Task implements Delayed {

	public TaskWaitingRun() {
	}

	public TaskWaitingRun(String name, String startTime, String endTime) {
		super(name, startTime, endTime);
	}
	/**
	 * 获取任务剩余存活时间
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		long now = System.currentTimeMillis();
		try {
			return DateUtil.stringToLong(super.startTime) - now;
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
				return (int) (DateUtil.stringToLong(o1.startTime) - DateUtil.stringToLong(this.startTime));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1; // TODO*/
	}
}
