package com.kael.asynctask.impl;

import java.util.Date;
import java.util.Properties;

import com.kael.asynctask.Task;
import com.kael.asynctask.TaskState;

/**
 * 类 TaskReferenceImpl 的实现描述：TODO 类实现描述
 * 
 */
public class CommonTaskReference extends TaskReferenceBase {

    private Task _task;
    private TaskState   _state;
    private String      _msg;
    private int         _progress;
    private Date        _startedTime;
    private Date        _finishedTime;
    private Object      _result;
    private String      _flag;

    private boolean     _canModify = false;
    private Properties  _props;

    /**
     * @param taskId
     * @param canModify
     */
    public CommonTaskReference(Task task, boolean canModify) {
        this._task = task;
        this._canModify = canModify;
    }

    /**
     */
    @Override
    public boolean isFinished() {
        if (_state == TaskState.success || _state == TaskState.failed
                || _state == TaskState.timeout) {
            return true;
        }
        return false;
    }

    /**
     */
    @Override
    public String getProperty(String key) {
        if (_props != null) {
            return _props.getProperty(key);
        }
        return null;
    }

    /**
     */
    @Override
    public void setProperty(String key, String value) {
        if (_canModify) {
            if (_props == null) {
                _props = new Properties();
            }
            _props.setProperty(key, value);
        }

    }

    /**
     * @author ligh4 2015年1月20日上午11:17:43
     */
    @Override
    public String getType() {
        return _task.getType();
    }

    /**
     */
    @Override
    public String getSubType() {
        return _task.getSubType();
    }

    /**
     */
    @Override
    public String getId() {
        return _task.getId();
    }

    /**
     */
    @Override
    public TaskState getState() {
        return _state;
    }

    /**
     */
    @Override
    public int getTimeoutMillis() {
        return _task.getTimeoutMillions();
    }

    /**
     */
    @Override
    public boolean isNeverTimeout() {
        return _task.isNeverTimeOut();
    }

    /**
     */
    @Override
    public Date getStartedTime() {
        return _startedTime;
    }

    /**
     */
    @Override
    public void setState(TaskState state, String msg) {
        if (_canModify) {
            if (_state == null) {
                _startedTime = new Date();
            }
            _state = state;
            _msg = msg;

            if (isFinished()) {
                _finishedTime = new Date();
            }
        }

    }

    /**
     */
    @Override
    public void setProgress(int progress) {
        if (_canModify) {
            _progress = progress;
        }
    }

    /**
     */
    @Override
    public Object getInput() {
        return _task.getInput();
    }

    /**
     */
    @Override
    public void setResult(Object result) {
        if (_canModify) {
            _result = result;
        }

    }

    /**
     * @author ligh4 2015年1月20日下午3:51:58
     */
    @Override
    public Date getFinishedTime() {
        return _finishedTime;
    }

    /**
     */
    @Override
    public String getFlag() {
        return _flag;
    }

    /**
     */
    @Override
    public void setFlag(String flag) {
        if (_canModify) {
            _flag = flag;
        }

    }

    /**
     */
    @Override
    public String getMsg() {
        return _msg;
    }

    /**
     */
    @Override
    public int getProgress() {
        return _progress;
    }

    /**
     */
    @Override
    public Object getResult() {
        return _result;
    }

    /**
     */
    @Override
    public boolean isRecoveredTask() {
        return false;
    }

    /**
     */
    @Override
    public boolean isPresistenceTask() {
        return false;
    }

}