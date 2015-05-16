/**
 * Project:      AsyncTask
 * FileName:     IBaseTaskExecutor.java
 * @version      V1.0 
 * Createdate:   2015年1月16日 上午10:57:43
 */
package com.kael.asynctask;

/**
 * 类 IBaseTaskExecutor 的实现描述：TODO 类实现描述
 * 
 */
public abstract class IBaseTaskExecutor {

    public enum TaskExecutorType {
        ExecuteTask,
        TimerTask;
    }

    private IBaseTaskExecutor _nextExecutor;

    public IBaseTaskExecutor getNextExecutor() {
        return _nextExecutor;
    }

    public void setNextExecutor(IBaseTaskExecutor executor) {
        _nextExecutor = executor;
    }

    public TaskExecutorType getTaskExecutorType() {
        return TaskExecutorType.ExecuteTask;
    }

}
