package com.kael.asynctask.impl;

import java.util.Date;
import java.util.Properties;

import com.kael.asynctask.TaskState;
import com.kael.asynctask.dao.TaskDao;
import com.kael.asynctask.util.ObjectSerialization;
import com.kael.mybatis.gen.TaskRecord;

/**
 * 类 PresistenceTaskReference 的实现描述：TODO 类实现描述
 * 
 */
public class PresistenceTaskReference extends TaskReferenceBase {

    private String  _taskId;
    private String  _type;
    private String  _subType;
    private Integer _timeoutMillis;
    private Date    _startedTime;
    private Object  _input;

    private boolean _isRecoveredTask = false;
    private boolean _canModify       = false;

    private TaskDao _taskDao         = new TaskDao();

    /**
     * @param taskId
     * @param canModify
     */
    public PresistenceTaskReference(String taskId, boolean canModify) {
        this._taskId = taskId;
        this._canModify = canModify;

        initBaseAtrribule();
    }

    @Override
    public boolean isFinished() {
        TaskState state = TaskState.valueOf(_taskDao.getTask(_taskId).getState());
//        if (state == TaskState.success || state == TaskState.failed || state == TaskState.timeout) {
//            return true;
//        }
        return TaskState.isFinished(state);
    }

    @Override
    public String getProperty(String key) {

        Properties props = (Properties) ObjectSerialization.FromString(_taskDao.getTask(_taskId)
                .getProps());
        if (props != null) {
            return props.getProperty(key);
        }
        return null;
    }

    @Override
    public void setProperty(String key, String value) {
        if (_canModify) {
            Properties props = (Properties) ObjectSerialization.FromString(_taskDao
                    .getTask(_taskId).getProps());
            if (props == null) {
                props = new Properties();
            }
            props.setProperty(key, value);

            String newJson = ObjectSerialization.ToString(props);
            TaskRecord record = new TaskRecord();
            record.setId(_taskId);
            record.setProps(newJson);

            _taskDao.updateTask(record);
        }

    }

    @Override
    public String getType() {
        if (_type == null) {
            initBaseAtrribule();
        }
        return _type;
    }

    @Override
    public String getSubType() {
        if (_subType == null) {
            initBaseAtrribule();
        }
        return _subType;
    }

    @Override
    public String getId() {
        return _taskId;
    }

    @Override
    public TaskState getState() {
        return TaskState.valueOf(_taskDao.getTask(_taskId).getState());
    }

    @Override
    public int getTimeoutMillis() {
        if (_timeoutMillis == null) {
            initBaseAtrribule();
        }
        return _timeoutMillis;
    }

    @Override
    public boolean isNeverTimeout() {
        if (getTimeoutMillis() <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public Date getStartedTime() {
        if (_startedTime == null) {
            initBaseAtrribule();
        }

        return _startedTime;
    }

    @Override
    public void setState(TaskState state, String msg) {
        if (_canModify) {

            TaskRecord record = new TaskRecord();
            record.setId(_taskId);
            record.setState(state.toString());
            record.setMsg(msg);

            if (TaskState.isFinished(state)) {
                record.setFinishtime(new Date());
            }
            _taskDao.updateTask(record);
        }
    }

    @Override
    public void setProgress(int progress) {
        if (_canModify) {
            TaskRecord record = new TaskRecord();
            record.setId(_taskId);
            record.setProgress(progress);
            _taskDao.updateTask(record);
        }

    }

    @Override
    public Object getInput() {
        if (_input == null) {
            initBaseAtrribule();
        }

        return _input;
    }

    @Override
    public void setResult(Object result) {
        if (_canModify && result != null) {

            String str = ObjectSerialization.ToString(result);

            TaskRecord record = new TaskRecord();
            record.setId(_taskId);
            record.setOutputdata(str);
            _taskDao.updateTask(record);
        }

    }

    /**
     */
    @Override
    public String getMsg() {
        return _taskDao.getTask(_taskId).getMsg();
    }

    /**
     */
    @Override
    public java.util.Date getFinishedTime() {
        return _taskDao.getTask(_taskId).getFinishtime();
    }

    /**
     */
    @Override
    public String getFlag() {

        return _taskDao.getTask(_taskId).getFlag();
    }

    /**
     */
    @Override
    public void setFlag(String flag) {
        if (_canModify) {
            TaskRecord record = new TaskRecord();
            record.setId(_taskId);
            record.setFlag(flag);
            _taskDao.updateTask(record);
        }

    }

    /**
     */
    @Override
    public int getProgress() {
        Integer progress = _taskDao.getTask(_taskId).getProgress();
        if (progress == null) {
            return 0;
        }

        return progress;
    }

    /**
     */
    @Override
    public Object getResult() {
        String str = _taskDao.getTask(_taskId).getOutputdata();
        if (str != null) {
            return ObjectSerialization.FromString(str);
        }
        return null;

    }

    private void initBaseAtrribule() {
        TaskRecord record = _taskDao.getTask(_taskId);
        _type = record.getType();
        _subType = record.getSubtype();
        if (record.getInputdata() != null) {
            _input = ObjectSerialization.FromString(record.getInputdata());
        }
        _timeoutMillis = record.getTimeoutmillis();
        _startedTime = record.getStarttime();

    }

    public void setIsRecoveredTask(boolean flag) {
        _isRecoveredTask = flag;
    }

    public boolean isRecoveredTask() {
        return _isRecoveredTask;
    }

    /**
     */
    @Override
    public boolean isPresistenceTask() {
        return true;
    }

}