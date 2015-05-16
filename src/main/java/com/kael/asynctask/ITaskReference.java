/**
 * Project:      AsyncTask
 * FileName:     IManageTaskReference.java
 * @version      V1.0 
 * Createdate:   2015年1月12日 下午2:55:06
 */
package com.kael.asynctask;

import java.util.Date;

/**
 * 类 IManageTaskReference 的实现描述：TODO 类实现描述
 * 
 */
public interface ITaskReference {

    public String getType();

    public String getSubType();

    public String getId();

    public boolean isFinished();

    public TaskState getState();

    public String getMsg();

    public int getTimeoutMillis();

    public boolean isNeverTimeout();

    public Date getStartedTime();

    public Date getFinishedTime();

    public String getProperty(String key);

    public String getFlag();

    public int getProgress();

    public Object getResult();

    public Object getInput();

    public boolean isRecoveredTask();

    public boolean isPresistenceTask();

    public void waitForTask();

}
