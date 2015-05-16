/**
 * Project:      AsyncTask
 * FileName:     TestTaskDao.java
 * @Description: TODO
 * @author:      ligh4
 * @version      V1.0 
 * Createdate:   2015年3月12日 上午11:12:41
 * Copyright:    Copyright(C) 2014-2015
 * Company       Lenovo LTD.
 * All rights Reserved, Designed By Lenovo CIC.
 */
package test;

import com.lenovo.asynctask.TaskCollector;
import com.lenovo.asynctask.dao.TaskDao;
import com.lenovo.asynctask.util.LogHelper;

/**
 * 类 TestTaskDao 的实现描述：TODO 类实现描述
 * 
 * @author ligh4 2015年3月12日上午11:12:41
 */
public class TestTaskDao {

    public static void main(String[] args) {
        LogHelper.debug("system start.");
        TaskDao dao = new TaskDao();
        dao.findTasks(new TaskCollector());
        LogHelper.debug("system exit.");
    }
}
