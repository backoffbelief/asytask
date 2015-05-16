package com.kael.asynctask.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kael.asynctask.Task;
import com.kael.asynctask.TaskCollector;
import com.kael.asynctask.TaskState;
import com.kael.asynctask.util.ObjectSerialization;
import com.kael.mybatis.MyBatis;
import com.kael.mybatis.gen.TaskRecord;
import com.kael.mybatis.gen.TaskRecordCriteria;
import com.kael.mybatis.gen.TaskRecordMapper;

/**
 * 类 TaskDao 的实现描述：TODO 类实现描述
 * 
 */
public class TaskDao {

    public boolean insertNewTask(Task task) {

        TaskRecord record = new TaskRecord();
        record.setId(task.getId());
        record.setType(task.getType());
        record.setSubtype(task.getSubType());
        record.setState(TaskState.queued.toString());
        record.setStarttime(new Date());
        record.setFlag(task.getFlag());
        if (task.getInput() != null) {
            String input = "";
            try {
                input = ObjectSerialization.ToString(task.getInput());
            } catch (Exception e) {
                return false;
            }
            record.setInputdata(input);
        }
        record.setTimeoutmillis(task.getTimeoutMillions());

        SqlSession session = MyBatis.getSessionFactory().openSession();
        //synchronized (session) 
        {
            TaskRecordMapper mapper = session.getMapper(TaskRecordMapper.class);
            mapper.insertSelective(record);
            session.commit();
            session.close();
        }

        return true;

    }

    public TaskRecord getTask(String taskId) {

        SqlSession session = MyBatis.getSessionFactory().openSession();
        //synchronized (session) 
        {
            TaskRecordMapper mapper = session.getMapper(TaskRecordMapper.class);
            TaskRecord record = mapper.selectByPrimaryKey(taskId);
            session.close();
            return record;
        }

    }

    public void updateTask(TaskRecord record) {
        SqlSession session = MyBatis.getSessionFactory().openSession();
        // (session) 
        {
            TaskRecordMapper mapper = session.getMapper(TaskRecordMapper.class);
            mapper.updateByPrimaryKeySelective(record);
            session.commit();
            session.close();
        }

    }

    public List<String> getRunningTasks() {

        TaskRecordCriteria example = new TaskRecordCriteria();
        TaskRecordCriteria.Criteria criteria = example.createCriteria();

        List<String> runningStates = new ArrayList<String>();
        runningStates.add(TaskState.queued.toString());
        runningStates.add(TaskState.running.toString());
        criteria.andStateIn(runningStates);

        List<TaskRecord> taskList = null;
        SqlSession session = MyBatis.getSessionFactory().openSession();
        //synchronized (session) 
        {
            TaskRecordMapper mapper = session.getMapper(TaskRecordMapper.class);
            taskList = mapper.selectByExample(example);
            session.close();
        }

        List<String> taskIds = new ArrayList<String>();
        for (TaskRecord record : taskList) {
            taskIds.add(record.getId());
        }
        return taskIds;

    }

    public List<String> findTasks(TaskCollector collector) {

        TaskRecordCriteria example = new TaskRecordCriteria();
        TaskRecordCriteria.Criteria criteria = example.createCriteria();

        List<String> ids = collector.getTaskIds();
        if (ids != null && ids.size() > 0) {
            criteria.andIdIn(ids);
        }

        List<String> types = collector.getTaskTypes();
        if (types != null && types.size() > 0) {
            criteria.andTypeIn(types);
        }

        List<String> subtypes = collector.getTaskSubTypes();
        if (subtypes != null && subtypes.size() > 0) {
            criteria.andSubtypeIn(subtypes);
        }

        List<String> flags = collector.getTaskFlags();
        if (flags != null && flags.size() > 0) {
            criteria.andFlagIn(flags);
        }

        List<TaskState> states = collector.getTaskStates();
        if (states != null && states.size() > 0) {
            List<String> stateList = new ArrayList<String>();
            for (TaskState state : states) {
                stateList.add(state.toString());
            }
            criteria.andStateIn(stateList);
        }

        Date taskStartTime_begin = collector.getTaskStartTime_Begin();
        if (taskStartTime_begin != null) {
            criteria.andStarttimeGreaterThanOrEqualTo(taskStartTime_begin);
        }

        Date taskStartTime_end = collector.getTaskStartTime_End();
        if (taskStartTime_end != null) {
            criteria.andStarttimeLessThanOrEqualTo(taskStartTime_end);
        }

        Date taskFinishTime_begin = collector.getTaskFinishTime_Begin();
        if (taskFinishTime_begin != null) {
            criteria.andFinishtimeGreaterThanOrEqualTo(taskFinishTime_begin);
        }

        Date taskFinishTime_end = collector.getTaskFinishTime_End();
        if (taskFinishTime_end != null) {
            criteria.andFinishtimeLessThanOrEqualTo(taskFinishTime_end);
        }

        List<TaskRecord> tasks = null;
        SqlSession session = MyBatis.getSessionFactory().openSession();
        //synchronized (session) 
        {
            TaskRecordMapper mapper = session.getMapper(TaskRecordMapper.class);
            tasks = mapper.selectByExample(example);
            session.close();
        }

        List<String> taskIds = new ArrayList<String>();
        for (TaskRecord task : tasks) {
            taskIds.add(task.getId());
        }
        return taskIds;
    }

}