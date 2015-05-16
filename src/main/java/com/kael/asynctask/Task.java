/**
 * Project:      AsyncTask
 * FileName:     Task.java
 * @version      V1.0 
 * Createdate:   2015年1月16日 上午9:31:05
 */
package com.kael.asynctask;

import com.kael.asynctask.util.IDGenerator;


/**
 * 类 ExecuteTask 的实现描述：TODO 类实现描述
 * 
 */
public class Task {

    private String  _id;
    private String  _type;
    private String  _subType;
    private Object  _inParams;
    private int     _timeoutMills    = 15000;
    private boolean _needPersistence = false;
    private String  _flag;

    public Task(String taskType, Object inputParam) {
        _id = IDGenerator.gen();
        _type = taskType;
        _inParams = inputParam;
    }

    public Task(String taskType, Object inputParam, int timeoutMills) {
        _id = IDGenerator.gen();
        _type = taskType;
        _inParams = inputParam;
        _timeoutMills = timeoutMills;
    }

    public Task(String taskType, Object inputParam, String parentId) {
        _id = IDGenerator.gen();
        _type = taskType;
        _inParams = inputParam;
    }

    public Task(String taskType, Object inputParam, String parentId, int timeoutMills) {
        _id = IDGenerator.gen();
        _type = taskType;
        _inParams = inputParam;
        _timeoutMills = timeoutMills;
    }

    public String getId() {
        return _id;
    }

    public String getType() {
        return _type;
    }

    public String getSubType() {
        return _subType;
    }

    public Object getInput() {
        return _inParams;
    }

    public int getTimeoutMillions() {
        return _timeoutMills;
    }

    public void setTimeoutMillions(int timeoutMills) {
        _timeoutMills = timeoutMills;
    }

    public boolean isNeverTimeOut() {
        if (_timeoutMills <= 0) {
            return true;
        }
        return false;
    }

    public void setNeedPersistence(boolean flag) {
        _needPersistence = flag;
    }

    public boolean getNeedPersistence() {
        return _needPersistence;
    }

    public void setFlag(String flag) {
        _flag = flag;
    }

    public String getFlag() {
        return _flag;
    }
}
