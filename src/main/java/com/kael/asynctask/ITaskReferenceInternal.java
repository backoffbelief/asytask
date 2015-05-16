/**
 * Project:      AsyncTask
 * FileName:     ITaskReferenceInternal.java
 * @version      V1.0 
 * Createdate:   2015年1月21日 下午3:56:55
 */
package com.kael.asynctask;

/**
 * 类 ITaskReferenceInternal 的实现描述：TODO 类实现描述
 * 
 */
public interface ITaskReferenceInternal extends ITaskReference {

    public void setFlag(String flag);

    public void setState(TaskState state, String msg);

    public void setProgress(int progress);

    public void setProperty(String key, String value);

    public void setResult(Object result);

}
