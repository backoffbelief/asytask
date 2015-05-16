/**
 * Project:      AsyncTask
 * FileName:     MyTask.java
 * @Description: TODO
 * @author:      ligh4
 * @version      V1.0 
 * Createdate:   2015年3月12日 下午2:42:56
 * Copyright:    Copyright(C) 2014-2015
 * Company       Lenovo LTD.
 * All rights Reserved, Designed By Lenovo CIC.
 */
package test.mytask;

import com.lenovo.asynctask.Task;

/**
 * 类 MyTask 的实现描述：TODO 类实现描述
 * 
 * @author ligh4 2015年3月12日下午2:42:56
 */
public class MyTask extends Task {

    /**
     * @param taskType
     * @param inputParam
     * @param timeoutMills
     */
    public MyTask(Object inputParam, int timeoutMills) {
        super(MyTask.class.getSimpleName(), inputParam, timeoutMills);
        setNeedPersistence(true);
    }

}
