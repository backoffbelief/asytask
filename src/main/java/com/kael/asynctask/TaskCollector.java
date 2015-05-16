/**
 * Project:      AsyncTask
 * FileName:     TaskCollector.java
 * @version      V1.0 
 * Createdate:   2015年1月12日 下午3:17:25
 */
package com.kael.asynctask;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 类 TaskCollector 的实现描述：TODO 类实现描述
 * 
 */
public class TaskCollector {

    private List<String>    types                = null;
    private List<String>    subTypes             = null;
    private List<String>    ids                  = null;
    private List<String>    flags                = null;
    private List<TaskState> taskStates           = null;
    private Date            taskStartTime_begin  = null;
    private Date            taskStartTime_end    = null;
    private Date            taskFinishTime_begin = null;
    private Date            taskFinishTime_end   = null;

    public List<String> getTaskTypes() {
        return types;
    }

    public List<String> getTaskSubTypes() {
        return subTypes;
    }

    public List<String> getTaskIds() {
        return ids;
    }

    public List<String> getTaskFlags() {
        return flags;
    }

    public List<TaskState> getTaskStates() {
        return taskStates;
    }

    public Date getTaskStartTime_Begin() {
        return taskStartTime_begin;
    }

    public Date getTaskStartTime_End() {
        return taskStartTime_end;
    }

    public Date getTaskFinishTime_Begin() {
        return taskFinishTime_begin;
    }

    public Date getTaskFinishTime_End() {
        return taskFinishTime_end;
    }

    public void setTaskTypeFilter(String[] types) {
        this.types = Arrays.asList(types);
    }

    public void setTaskSubTypeFilter(String[] subTypes) {
        this.subTypes = Arrays.asList(subTypes);
    }

    public void setTaskIdFilter(String[] ids) {
        this.ids = Arrays.asList(ids);
    }

    public void setTaskFlagFilter(String[] flags) {
        this.flags = Arrays.asList(flags);
    }

    public void setTaskStateFilter(TaskState[] states) {
        this.taskStates = Arrays.asList(states);
    }

    public void setTaskStartTimeFilter(Date beginTime, Date endTime) {
        this.taskStartTime_begin = beginTime;
        this.taskStartTime_end = endTime;
    }

    public void setTaskFinishedFilter(Date beginTime, Date endTime) {
        this.taskFinishTime_begin = beginTime;
        this.taskFinishTime_end = endTime;
    }

    public boolean isInCollector(ITaskReference taskRef) {
        if (taskRef == null) {
            return false;
        }

        if (types != null && types.size() > 0) {
            if (!types.contains(taskRef.getType())) {
                return false;
            }
        }

        if (subTypes != null && subTypes.size() > 0) {
            if (taskRef.getSubType() == null || !subTypes.contains(taskRef.getSubType())) {
                return false;
            }
        }

        if (ids != null && ids.size() > 0) {
            if (!ids.contains(taskRef.getId())) {
                return false;
            }
        }

        if (flags != null && flags.size() > 0) {
            if (taskRef.getFlag() != null && !flags.contains(taskRef.getFlag())) {
                return false;
            }
        }

        if (taskStates != null && taskStates.size() > 0) {
            if (!taskStates.contains(taskRef.getState())) {
                return false;
            }
        }

        if (taskRef.getStartedTime() != null) {
            if (taskStartTime_begin != null
                    && taskRef.getStartedTime().before(taskFinishTime_begin)) {
                return false;
            }

            if (taskStartTime_end != null && taskRef.getStartedTime().after(taskStartTime_end)) {
                return false;
            }
        }

        if (taskRef.getFinishedTime() != null) {
            if (taskFinishTime_begin != null
                    && taskRef.getFinishedTime().before(taskFinishTime_begin)) {
                return false;
            }

            if (taskFinishTime_end != null && taskRef.getFinishedTime().after(taskFinishTime_end)) {
                return false;
            }
        }

        return true;
    }
}
