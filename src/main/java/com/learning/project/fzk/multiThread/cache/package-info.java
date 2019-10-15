package com.learning.project.fzk.multiThread.cache;
/*
 * 使用多种方式实现的缓存工具类包
 * Cache1：使用Future占位符实现，有多线程同时计算一个key的浪费问题
 * Cache2：使用ConcurrentHashMap#putIfAbsent实现
 * Cache3：使用ConcurrentHashMap#computeIfAbsent实现
 */