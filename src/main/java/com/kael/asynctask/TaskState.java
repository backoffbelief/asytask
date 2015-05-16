/**
 * Project:      AsyncTask
 * FileName:     TaskState.java
 * @version      V1.0 
 * Createdate:   2015年1月16日 上午10:44:15
 */
package com.kael.asynctask;

public enum TaskState {
    unknown(0),
    queued(1),
    running(2),
    success(3),
    failed(4),
    timeout(5);

    private int _val;

    private TaskState(int val) {
        _val = val;
    }

    public int value() {
        return _val;
    }

    public static boolean isFinished(TaskState state) {
        if (state == success || state == failed || state == timeout) {
            return true;
        }
        return false;
    }

    public static TaskState valueOf(int value) {
        switch (value) {
            case 1:
                return queued;
            case 2:
                return running;
            case 3:
                return success;
            case 4:
                return failed;
            case 5:
                return timeout;

            default:
                return unknown;
        }
    }
}
