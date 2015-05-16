package com.kael.asynctask.impl;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类 GlobalThreadPool 的实现描述：TODO 类实现描述
 * 
 */
public class GlobalTaskExecutePool {

    private static ThreadPoolExecutor    _thredPool;
    @SuppressWarnings("unused")
    private static GlobalTaskExecutePool _GlobalThredPool = new GlobalTaskExecutePool();

    private GlobalTaskExecutePool() {
        initExecutorPool();
    }

    private void initExecutorPool() {

        _thredPool = new ThreadPoolExecutor(4, 10, 5000, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public static Future<?> submit(Runnable run) {
        return _thredPool.submit(run);
    }

    public static void stop() {
        _thredPool.shutdown();
    }
}