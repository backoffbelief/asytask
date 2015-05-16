package com.kael.asynctask.impl;

import com.kael.asynctask.IBaseTaskExecutor;
import com.kael.asynctask.IBaseTaskExecutor.TaskExecutorType;
import com.kael.asynctask.ITaskExecutor;
import com.kael.asynctask.ITaskReferenceInternal;
import com.kael.asynctask.ITimerTaskExecutor;
import com.kael.asynctask.TaskState;
import com.kael.asynctask.util.LogHelper;

/**
 * 类 TimerTaskExecutorThread 的实现描述：TODO 类实现描述
 * 
 */
public class TimerTaskExecutorThread implements Runnable {

    private ITaskReferenceInternal _task;
    private ITimerTaskExecutor     _executor;

    public TimerTaskExecutorThread(ITaskReferenceInternal task, ITimerTaskExecutor executor) {
        // TODO Auto-generated constructor stub
        _task = task;
        _executor = executor;
    }

    /**
     * @author ligh4 2015年2月2日下午1:56:37
     */
    @Override
    public void run() {

        _task.setState(TaskState.running, null);
        Object result = _executor.execute(_task);

        if (_executor.isFinished()) {
            GlobalTimerTaskExecutePool.stopTask(this);

            IBaseTaskExecutor nextExecotor = _executor.getNextExecutor();
            if (nextExecotor != null) {

                if (nextExecotor.getTaskExecutorType() == TaskExecutorType.ExecuteTask) {

                    GlobalTaskExecutePool.submit(new TaskExecutorThread(_task,
                            (ITaskExecutor) nextExecotor));

                } else if (_executor.getNextExecutor().getTaskExecutorType() == TaskExecutorType.TimerTask) {

                    GlobalTimerTaskExecutePool.scheduleTask(new TimerTaskExecutorThread(_task,
                            (ITimerTaskExecutor) nextExecotor), 0,
                            ((ITimerTaskExecutor) nextExecotor).intervalsMills());
                } else {
                    LogHelper.error("unknown TaskExecutorType '"
                            + nextExecotor.getTaskExecutorType().toString() + "'.");
                }

            } else {
                if (_task.getState() == TaskState.running) {
                    _task.setResult(result);
                    _task.setState(TaskState.success, "success");
                }
            }

            return;
        }

    }

}