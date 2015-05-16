/**
 * Project:      AsyncTask
 * FileName:     TestContinueTask.java
 * @Description: TODO
 * @author:      ligh4
 * @version      V1.0 
 * Createdate:   2015年3月23日 上午9:42:14
 * Copyright:    Copyright(C) 2014-2015
 * Company       Lenovo LTD.
 * All rights Reserved, Designed By Lenovo CIC.
 */
package test.mytask;

import java.util.List;

import com.lenovo.asynctask.ITaskFinishedCallback;
import com.lenovo.asynctask.ITaskReference;
import com.lenovo.asynctask.TaskCollector;
import com.lenovo.asynctask.TaskManager;
import com.lenovo.asynctask.TaskState;
import com.lenovo.asynctask.util.DateUtil;
import com.lenovo.asynctask.util.LogHelper;

/**
 * 类 TestContinueTask 的实现描述：TODO 类实现描述
 * 
 * @author ligh4 2015年3月23日上午9:42:14
 */
public class TestContinueTask {

    /**
     * @author ligh4 2015年3月12日下午2:52:45
     * @param args
     */
    public static void main(String[] args) throws Exception {
        TaskManager.instance().start();

        List<ITaskReference> tasks = queryRunningTasks();
        if (tasks == null || tasks.size() == 0) {
            submitAndWaitTask();
        } else {
            for (ITaskReference taskReference : tasks) {
                queryTaskProgress(taskReference);
            }
        }

        TaskManager.instance().stop();
    }

    public static void submitAndWaitTask() throws Exception {
        MyTask task = new MyTask("liguanghui", 200000);
        ITaskReference taskReference = TaskManager.instance().submitTask(task,
                new ITaskFinishedCallback() {

                    @Override
                    public void onTaskFinished(ITaskReference taskRef) {
                        LogHelper.debug(taskRef.getId() + ";" + taskRef.getState().toString() + ";"
                                + DateUtil.format(taskRef.getStartedTime()) + "  "
                                + DateUtil.format(taskRef.getFinishedTime()) + ";"
                                + taskRef.getResult().toString());

                    }
                });

        queryTaskProgress(taskReference);
    }

    public static void queryTaskProgress(ITaskReference taskReference) throws Exception {
        String taskID = taskReference.getId();
        while (!taskReference.isFinished()) {
            LogHelper.debug(taskID + ": progress " + taskReference.getProgress());
            Thread.sleep(1000);
        }
        LogHelper.debug(taskID + ": finished. ");
    }

    public static List<ITaskReference> queryRunningTasks() {
        TaskCollector collector = new TaskCollector();
        collector.setTaskStateFilter(new TaskState[] { TaskState.running });
        collector.setTaskTypeFilter(new String[] { MyTask.class.getSimpleName() });
        return TaskManager.instance().findTasks(collector);

    }
}
