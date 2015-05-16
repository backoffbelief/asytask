/**
 * Project:      AsyncTask
 * FileName:     ITimerTaskExecutor.java
 * @version      V1.0 
 * Createdate:   2015年1月16日 上午10:44:15
 */
package com.kael.asynctask;

/**
 * 类 ITimerTaskExecutor 的实现描述：TODO 类实现描述
 * 
 * @author ligh4 2015年1月16日上午10:44:15
 */
public abstract class ITimerTaskExecutor extends IBaseTaskExecutor {

    public abstract int intervalsMills();

    public abstract boolean isFinished();

    public abstract Object execute(ITaskReferenceInternal taskRef);

}
