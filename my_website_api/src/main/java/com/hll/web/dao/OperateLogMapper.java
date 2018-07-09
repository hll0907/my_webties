package com.hll.web.dao;

import java.util.List;

import com.hll.web.pojo.OperateLog;

public interface OperateLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    OperateLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OperateLog record);

    int updateByPrimaryKey(OperateLog record);
    
    List<OperateLog> selectByUserId(Integer userId);
    
    List<OperateLog> selectAll();
}