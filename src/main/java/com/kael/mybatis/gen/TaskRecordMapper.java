package com.kael.mybatis.gen;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TaskRecordMapper {
    int countByExample(TaskRecordCriteria example);

    int deleteByExample(TaskRecordCriteria example);

    int deleteByPrimaryKey(String id);

    int insert(TaskRecord record);

    int insertSelective(TaskRecord record);

    List<TaskRecord> selectByExample(TaskRecordCriteria example);

    TaskRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TaskRecord record, @Param("example") TaskRecordCriteria example);

    int updateByExample(@Param("record") TaskRecord record, @Param("example") TaskRecordCriteria example);

    int updateByPrimaryKeySelective(TaskRecord record);

    int updateByPrimaryKey(TaskRecord record);
}