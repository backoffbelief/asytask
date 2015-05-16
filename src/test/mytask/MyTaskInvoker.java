/**
 * Project:      AsyncTask
 * FileName:     MyTaskInvoker.java
 * @Description: TODO
 * @author:      ligh4
 * @version      V1.0 
 * Createdate:   2015年3月12日 下午2:52:45
 * Copyright:    Copyright(C) 2014-2015
 * Company       Lenovo LTD.
 * All rights Reserved, Designed By Lenovo CIC.
 */
package test.mytask;

import com.lenovo.asynctask.ITaskFinishedCallback;
import com.lenovo.asynctask.ITaskReference;
import com.lenovo.asynctask.TaskManager;
import com.lenovo.asynctask.util.DateUtil;
import com.lenovo.asynctask.util.LogHelper;

/**
 * 类 MyTaskInvoker 的实现描述：TODO 类实现描述
 * 
 * @author ligh4 2015年3月12日下午2:52:45
 */
public class MyTaskInvoker {

    /**
     * @author ligh4 2015年3月12日下午2:52:45
     * @param args
     */
    public static void main(String[] args) throws Exception {
        TaskManager.instance().start();

        for (int i = 0; i < 10000; i++) {
            MyTask task = new MyTask("liguanghui" + i, 200000);
            TaskManager.instance().submitTask(task, new ITaskFinishedCallback() {

                @Override
                public void onTaskFinished(ITaskReference taskRef) {
                    LogHelper.debug(taskRef.getId() + ";" + taskRef.getState().toString() + ";"
                            + DateUtil.format(taskRef.getStartedTime()) + "  "
                            + DateUtil.format(taskRef.getFinishedTime()) + ";"
                            + taskRef.getResult().toString());

                }
            });
        }

        Thread.sleep(30000);

        TaskManager.instance().stop();
    }

}
