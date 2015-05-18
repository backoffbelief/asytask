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
    running(0x10),
    success(0x100),
    failed(0x1000),
    timeout(0x10000);

    private int _val;

    private TaskState(int val) {
        _val = val;
    }

    public int value() {
        return _val;
    }

//    public static boolean isFinished(TaskState state) {
//        if (state == success || state == failed || state == timeout) {
//            return true;
//        }
//        return false;
//    }

    public static boolean isFinished(TaskState state) {
    	if ((state._val & (success._val | failed._val | timeout._val)) != 0) {
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
