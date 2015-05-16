package com.kael.asynctask.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 类 GlobalTimerTaskExecutorPool 的实现描述：TODO 类实现描述
 * 
 * @author ligh4 2015年1月16日上午10:09:32
 */
public class GlobalTimerTaskExecutePool {

    private static ScheduledExecutorService                        _scheduleExecutorPool = Executors
                                                                                                 .newScheduledThreadPool(4);
    private static ConcurrentHashMap<Runnable, ScheduledFuture<?>> _taskFutures          = new ConcurrentHashMap<Runnable, ScheduledFuture<?>>();

    private GlobalTimerTaskExecutePool() {

    }

    public static void scheduleTask(Runnable run, int delayMills, int intervalMills) {
        ScheduledFuture<?> future = _scheduleExecutorPool.scheduleWithFixedDelay(run, delayMills,
                intervalMills, TimeUnit.MILLISECONDS);
        _taskFutures.put(run, future);
    }

    public static void stopTask(Runnable run) {
        ScheduledFuture<?> feture = _taskFutures.get(run);
        if (feture != null) {
            feture.cancel(false);
            _taskFutures.remove(run);
        }

    }

    public static void stop() {
        _scheduleExecutorPool.shutdown();
    }

}