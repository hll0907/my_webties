package com.hll.web.service;

import java.util.List;

import com.hll.web.pojo.OperateLog;

public interface OperateLogService {
	
	int insertSelective(OperateLog record);
	
	List<OperateLog> selectByUserId(Integer userId);
	
	List<OperateLog> selectAll();

	OperateLog selectByPrimaryKey(Integer id);
	
	int updateByPrimaryKeySelective(OperateLog record);
	
	int deleteByPrimaryKey(Integer id);
}
