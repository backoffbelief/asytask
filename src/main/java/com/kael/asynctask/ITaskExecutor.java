/**
 * Project:      AsyncTask
 * FileName:     IManageTaskExecutor.java
 * @Description: TODO
 * @version      V1.0 
 * Createdate:   2015年1月12日 下午2:51:57
 */
package com.kael.asynctask;

/**
 * 类 IManageTaskExecutor 的实现描述：TODO 类实现描述
 * 
 */
public abstract class ITaskExecutor extends IBaseTaskExecutor {

    public abstract Object execute(ITaskReferenceInternal taskRef);

    public abstract Object continue_execute(ITaskReferenceInternal taskRef);
}
