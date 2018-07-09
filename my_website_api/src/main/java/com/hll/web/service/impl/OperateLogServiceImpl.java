package com.hll.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hll.web.dao.OperateLogMapper;
import com.hll.web.pojo.OperateLog;
import com.hll.web.service.OperateLogService;

@Service
public class OperateLogServiceImpl implements OperateLogService {

	@Autowired
	OperateLogMapper operateLogMapper;

	@Override
	public int insertSelective(OperateLog record) {
		int insertSelective = operateLogMapper.insertSelective(record);
		if (insertSelective > 0) {
			return insertSelective;
		}
		return 0;
	}

	@Override
	public List<OperateLog> selectByUserId(Integer userId) {
		List<OperateLog> selectByUserId = operateLogMapper.selectByUserId(userId);
		if (selectByUserId.isEmpty()) {
			return null;
		}
		return selectByUserId;
	}

	@Override
	public List<OperateLog> selectAll() {
		List<OperateLog> selectAll = operateLogMapper.selectAll();
		if (selectAll.isEmpty()) {
			return null;
		}
		return selectAll;
	}

	@Override
	public OperateLog selectByPrimaryKey(Integer id) {
		OperateLog selectByPrimaryKey = operateLogMapper.selectByPrimaryKey(id);
		if (selectByPrimaryKey != null) {
			return selectByPrimaryKey;
		}
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(OperateLog record) {
		int updateByPrimaryKey = operateLogMapper.updateByPrimaryKey(record);
		if (updateByPrimaryKey > 0) {
			return updateByPrimaryKey;
		}
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		int deleteByPrimaryKey = operateLogMapper.deleteByPrimaryKey(id);
		if (deleteByPrimaryKey > 0) {
			return deleteByPrimaryKey;
		}
		return 0;
	}

}
