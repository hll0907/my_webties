package com.hll.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hll.web.dao.IntergralRecordsMapper;
import com.hll.web.dao.UserMapper;
import com.hll.web.pojo.IntergralRecords;
import com.hll.web.pojo.User;
import com.hll.web.service.IntergralService;
import com.hll.web.util.DateTimeUtil;

@Service
public class IntergralServiceImpl implements IntergralService {

	@Autowired
	IntergralRecordsMapper intergralRecordsMapper;
	@Autowired
	UserMapper userMapper;

	@Transactional
	@Override
	public int insert(IntergralRecords record) {
		int selective = intergralRecordsMapper.insertSelective(record);
		if (selective > 0) {
			return selective;
		}
		return 0;
	}

	@Override
	public IntergralRecords selectByPrimaryKey(Integer id) {
		IntergralRecords selectByPrimaryKey = intergralRecordsMapper.selectByPrimaryKey(id);
		if (selectByPrimaryKey != null) {
			return selectByPrimaryKey;
		}
		return null;
	}

	@Override
	public IntergralRecords selectByUserId(Integer userId) {
		IntergralRecords selectByUserId = intergralRecordsMapper.selectByUserId(userId);
		return selectByUserId;
	}

	@Override
	public List<IntergralRecords> selectIntergralRecordAll() {
		List<IntergralRecords> selectIntergralRecordAll = intergralRecordsMapper.selectIntergralRecordAll();
		if (selectIntergralRecordAll.isEmpty()) {
			return null;
		}
		return selectIntergralRecordAll;
	}

	@Override
	public List<IntergralRecords> selectIntergralByUserId(Integer userId) {
		List<IntergralRecords> selectIntergralByUserId = intergralRecordsMapper.selectIntergralByUserId(userId);
		if (selectIntergralByUserId.isEmpty()) {
			return null;
		}
		return selectIntergralByUserId;
	}

	@Transactional
	@Override
	public int updateByPrimaryKeySelective(IntergralRecords record) {
		String updateTime = DateTimeUtil.getStringDate();
		record.setUpdatedAt(updateTime);
		User user = new User();
		user.setId(record.getUserId());
		user.setIntegral(record.getIntergral());
		user.setUpdateTime(updateTime);
		int i = intergralRecordsMapper.updateByPrimaryKeySelective(record);
		if (i > 0) {
			userMapper.updateByPrimaryKeySelective(user);
			return i;
		}
		return 0;
	}

}
