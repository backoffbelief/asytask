/**
 * Project:      AsyncTask
 * FileName:     TestTaskExecutor.java
 * @Description: TODO
 * @author:      ligh4
 * @version      V1.0 
 * Createdate:   2015年3月12日 下午2:43:19
 * Copyright:    Copyright(C) 2014-2015
 * Company       Lenovo LTD.
 * All rights Reserved, Designed By Lenovo CIC.
 */
package test.mytask;

import com.lenovo.asynctask.ITaskExecutor;
import com.lenovo.asynctask.ITaskReferenceInternal;
import com.lenovo.asynctask.TaskState;
import com.lenovo.asynctask.util.LogHelper;

/**
 * 类 TestTaskExecutor 的实现描述：TODO 类实现描述
 * 
 * @author ligh4 2015年3月12日下午2:43:19
 */
public class MyTaskExecutor extends ITaskExecutor {

    /**
     * @author ligh4 2015年3月12日下午2:46:51
     */
    @Override
    public Object execute(ITaskReferenceInternal taskRef) {
        LogHelper.debug("begin execute MyTask...");

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                LogHelper.exception(e);
            }
            taskRef.setProgress(i + 1);
        }

        return taskRef.getInput().toString().toUpperCase();
    }

    /**
     * @author ligh4 2015年3月12日下午2:46:51
     */
    @Override
    public Object continue_execute(ITaskReferenceInternal taskRef) {
        if (taskRef.getState() == TaskState.running) {
            int i = taskRef.getProgress();
            for (; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    LogHelper.exception(e);
                }
                taskRef.setProgress(i + 1);
            }

            return taskRef.getInput().toString().toUpperCase();
        } else {
            taskRef.setState(TaskState.failed, "");
            return null;
        }

    }

}
