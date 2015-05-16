package com.kael.asynctask.impl;

import com.kael.asynctask.ITaskReferenceInternal;

/**
 * 类 TaskReferenceBase 的实现描述：TODO 类实现描述
 * 
 */
public abstract class TaskReferenceBase implements ITaskReferenceInternal {

    private boolean isFinishedEventFired = false;

    public boolean isFinishedEventFired() {
        return isFinishedEventFired;
    }

    public void setFinishedEventFired() {
        isFinishedEventFired = true;
    }

    /**
     */
    @Override
    public void waitForTask() {
        while (!isFinished()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }
    }

}