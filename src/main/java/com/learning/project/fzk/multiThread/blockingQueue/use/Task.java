package com.learning.project.fzk.multiThread.blockingQueue.use;

/**
 * 任务类
 * <p>
 * author:fanzhoukai
 * 2019/8/31 15:22
 */
public class Task {
	// 任务名称
	String name;
	// 开始时间
	String startTime;
	// 结束时间
	String endTime;

	public Task() {
	}

	public Task(String name, String startTime, String endTime) {
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}
}
