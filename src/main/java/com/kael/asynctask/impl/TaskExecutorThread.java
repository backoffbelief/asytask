package com.kael.asynctask.impl;

import java.util.Date;

import com.kael.asynctask.IBaseTaskExecutor;
import com.kael.asynctask.IBaseTaskExecutor.TaskExecutorType;
import com.kael.asynctask.ITaskExecutor;
import com.kael.asynctask.ITaskReferenceInternal;
import com.kael.asynctask.ITimerTaskExecutor;
import com.kael.asynctask.TaskState;
import com.kael.asynctask.util.LogHelper;

public class TaskExecutorThread implements Runnable {

    private ITaskReferenceInternal _task;
    private ITaskExecutor          _executor;

    public TaskExecutorThread(ITaskReferenceInternal task, ITaskExecutor executor) {
        // TODO Auto-generated constructor stub
        _task = task;
        _executor = executor;
    }

    /**
     */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (_executor == null || _task == null) {
            _task.setState(TaskState.failed, "invalid params");
            LogHelper.error("invalid params of task '" + _task.getId() + "'.");
            return;
        }
        // 判断是否排队状态
        if (_task.getState() == TaskState.timeout) {
            return;
        }

        // 判断是否timeout
        Date now = new Date();
        if (!_task.isNeverTimeout()
                && now.getTime() - _task.getStartedTime().getTime() >= _task.getTimeoutMillis()) {
            _task.setState(TaskState.timeout, "task has timeout");
            LogHelper.error("task '" + _task.getId() + "' has timeout.");
            return;
        }

        _task.setState(TaskState.running, null);
        Object result = null;
        try {
            if (_task.isRecoveredTask()) {
                result = _executor.continue_execute(_task);
            } else {
                result = _executor.execute(_task);
            }

        } catch (Exception e) {
            LogHelper.exception(e);
            if (!_task.isFinished()) {
                _task.setState(TaskState.failed, "task execute failed.");
            }

        }

        IBaseTaskExecutor nextExecotor = _executor.getNextExecutor();
        if (nextExecotor != null) {

            if (nextExecotor.getTaskExecutorType() == TaskExecutorType.ExecuteTask) {

                GlobalTaskExecutePool.submit(new TaskExecutorThread(_task,
                        (ITaskExecutor) nextExecotor));

            } else if (_executor.getNextExecutor().getTaskExecutorType() == TaskExecutorType.TimerTask) {

                GlobalTimerTaskExecutePool.scheduleTask(new TimerTaskExecutorThread(_task,
                        (ITimerTaskExecutor) nextExecotor), 0, ((ITimerTaskExecutor) nextExecotor)
                        .intervalsMills());
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

    }

}