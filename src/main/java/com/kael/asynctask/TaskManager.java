/**
 * Project:      AsyncTask
 * FileName:     TaskManager.java
 * @version      V1.0 
 * Createdate:   2015年1月12日 下午3:10:07
 */
package com.kael.asynctask;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.kael.asynctask.dao.TaskDao;
import com.kael.asynctask.impl.CommonTaskReference;
import com.kael.asynctask.impl.GlobalTaskExecutePool;
import com.kael.asynctask.impl.GlobalTimerTaskExecutePool;
import com.kael.asynctask.impl.PresistenceTaskReference;
import com.kael.asynctask.impl.TaskExecutorThread;
import com.kael.asynctask.impl.TaskReferenceBase;
import com.kael.asynctask.impl.TimerTaskExecutorThread;
import com.kael.asynctask.util.LogHelper;

/**
 * 类 ManageTaskManager 的实现描述：TODO 类实现描述
 * 
 */
public class TaskManager {

    private Map<String, ITaskFinishedCallback> _taskCallbacks = new HashMap<String, ITaskFinishedCallback>();

    private Map<String, IBaseTaskExecutor>     _taskExecutors = new HashMap<String, IBaseTaskExecutor>();
    private Map<String, TaskReferenceBase>     _runningTasks  = new ConcurrentHashMap<String, TaskReferenceBase>();

    private TaskDao                            taskDao        = new TaskDao();

    private static TaskManager                 _instance      = new TaskManager();

    public static TaskManager instance() {
        return _instance;
    }

    public ITaskReference submitTask(Task task) {

        TaskReferenceBase taskReference = null;
        if (task.getNeedPersistence()) {
            saveTaskToDB(task);
            taskReference = new PresistenceTaskReference(task.getId(), true);
        } else {
            taskReference = new CommonTaskReference(task, true);

        }
        _runningTasks.put(task.getId(), taskReference);
        taskReference.setState(TaskState.queued, "");
        submitTaskToExecotePool(taskReference);

        return taskReference;
    }

    public ITaskReference submitTask(Task task, ITaskFinishedCallback callback) {

        if (callback != null) {
            setTaskFinishedCallBack(task.getId(), callback);
        }
        return submitTask(task);
    }

    public ITaskReference retryTask(String taskId) {
        TaskReferenceBase taskReference = _runningTasks.get(taskId);
        if (taskReference == null) {
            if (taskDao.getTask(taskId) != null) {
                return new PresistenceTaskReference(taskId, false);
            }
        }
        if (taskReference == null) {
            LogHelper.error("can't find task reference by id.");
            return null;
        }

        if (!taskReference.isFinished()) {
            LogHelper.error("retry failed, task is running.");
            return taskReference;
        }

        taskReference.setState(TaskState.queued, "");
        submitTaskToExecotePool(taskReference);

        return taskReference;
    }

    public ITaskReference findTask(String taskId) {

        ITaskReference taskReference = _runningTasks.get(taskId);
        if (taskReference == null) {
            if (taskDao.getTask(taskId) != null) {
                return new PresistenceTaskReference(taskId, false);
            }
        }

        return taskReference;
    }

    public List<ITaskReference> findTasks(TaskCollector collector) {

        List<ITaskReference> references = new ArrayList<ITaskReference>();

        if (collector != null) {
            for (String id : _runningTasks.keySet()) {
                ITaskReference reference = _runningTasks.get(id);
                if (!reference.isPresistenceTask() && collector.isInCollector(reference)) {
                    references.add(reference);
                }
            }
        }

        List<String> taskIds = taskDao.findTasks(collector);
        for (String taskId : taskIds) {
            references.add(new PresistenceTaskReference(taskId, false));
        }

        return references;
    }

    private void setTaskFinishedCallBack(String taskId, ITaskFinishedCallback callback) {
        _taskCallbacks.put(taskId, callback);
    }

    public void start() {

        GlobalTimerTaskExecutePool.scheduleTask(new TaskTimeoutChecker(), 0, 100);
        GlobalTimerTaskExecutePool.scheduleTask(new TaskStateChecker(), 0, 100);

        loadTaskExecutors();
        loadRunningTasks();
    }

    public void stop() {
        GlobalTimerTaskExecutePool.stop();
        GlobalTaskExecutePool.stop();
    }

    private void loadTaskExecutors() {

        String config_file = "config/taskexecutors.properties";
        FileInputStream inputStream = null;
        Properties props = new Properties();
        try {
            props.load(new FileReader(config_file));

        } catch (InvalidPropertiesFormatException e) {
            LogHelper.exception(e);
        } catch (IOException e) {
            LogHelper.exception(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LogHelper.exception(e);
            }
        }

        Enumeration<Object> keys = props.keys();
        while (keys.hasMoreElements()) {
            String configName = keys.nextElement().toString();
            String fullClassName = props.getProperty(configName);
            Object instance = null;
            try {
                instance = Class.forName(fullClassName).newInstance();
            } catch (Exception e) {
                LogHelper.exception(e);
            }

            if (instance != null) {
                _taskExecutors.put(configName, (IBaseTaskExecutor) instance);
                LogHelper.debug("TaskExecutor '" + fullClassName + "' loaded.");
            }

        }
    }

    private void loadRunningTasks() {
        List<String> taskIds = taskDao.getRunningTasks();
        for (String taskId : taskIds) {
            //if (_runningTasks.get(taskId) == null) 
            {
                PresistenceTaskReference taskRef = new PresistenceTaskReference(taskId, true);
                taskRef.setIsRecoveredTask(true);
                _runningTasks.put(taskId, taskRef);
                submitTaskToExecotePool(taskRef);
            }
        }
    }

    private void saveTaskToDB(Task task) {
        taskDao.insertNewTask(task);
    }

    private void submitTaskToExecotePool(ITaskReferenceInternal taskRef) {
        String taskType = taskRef.getType();
        String taskSubType = taskRef.getSubType();
        String taskFullType = taskType;
        if (taskSubType != null && taskSubType.length() > 0) {
            taskFullType += "." + taskSubType;
        }

        IBaseTaskExecutor executor = _taskExecutors.get(taskFullType);
        if (executor == null) {

            taskRef.setState(TaskState.failed, "can not find executor of task type '"
                    + taskFullType + "'.");
            LogHelper.error("can not find executor of task type '" + taskFullType + "'.");
        } else {

            switch (executor.getTaskExecutorType()) {
                case ExecuteTask: {
                    TaskExecutorThread executorThread = new TaskExecutorThread(taskRef,
                            (ITaskExecutor) executor);
                    GlobalTaskExecutePool.submit(executorThread);
                    break;
                }

                case TimerTask: {
                    ITimerTaskExecutor timerTaskExecutor = (ITimerTaskExecutor) executor;
                    GlobalTimerTaskExecutePool.scheduleTask(new TimerTaskExecutorThread(taskRef,
                            timerTaskExecutor), 0, timerTaskExecutor.intervalsMills());
                    break;
                }

                default: {
                    taskRef.setState(TaskState.failed, "unknow TaskExecutorType '"
                            + executor.getTaskExecutorType().toString() + "'.");
                    LogHelper.error("unknow TaskExecutorType '"
                            + executor.getTaskExecutorType().toString() + "'.");
                }
            }

        }

    }

    private class TaskTimeoutChecker implements Runnable {
        @Override
        public void run() {
            for (ITaskReferenceInternal taskRef : _runningTasks.values()) {
                if (!taskRef.isFinished()
                        && !taskRef.isNeverTimeout()
                        && new Date().getTime() - taskRef.getStartedTime().getTime() >= taskRef
                                .getTimeoutMillis()) {
                    taskRef.setState(TaskState.timeout, "task has timeout.");
                }
            }
        }

    }

    private class TaskStateChecker implements Runnable {

        private final int TaskAliveMillisecondsAfterFinished = 10 * 60 * 1000;

        @Override
        public void run() {
            //LogHelper.debug("task count " + _runningTasks.size());
            Iterator<Entry<String, TaskReferenceBase>> it = _runningTasks.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, TaskReferenceBase> entry = it.next();
                TaskReferenceBase taskRef = entry.getValue();
                if (taskRef.isFinished()) {

                    //                    if (!taskRef.isFinishedEventFired()) {
                    //                        EventCenter.instance().fireEvent(new TaskFinishedEvent(taskRef));
                    //                        taskRef.setFinishedEventFired();
                    //                    }

                    // callback
                    ITaskFinishedCallback callback = _taskCallbacks.get(taskRef.getId());
                    if (callback != null) {
                        _taskCallbacks.remove(taskRef.getId());
                        callback.onTaskFinished(taskRef);
                    }

                    // delete finished tasks.
                    if (new Date().getTime() - taskRef.getFinishedTime().getTime() >= TaskAliveMillisecondsAfterFinished) {
                        it.remove();
                    }

                }
            }

        }
    }

}
