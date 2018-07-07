package com.hll.web.service;

import java.util.List;

import com.hll.web.pojo.IntergralRecords;

public interface IntergralService {

	int insert(IntergralRecords record);
	
	int updateByPrimaryKeySelective(IntergralRecords record);
	
	IntergralRecords selectByPrimaryKey(Integer id);
	
	IntergralRecords selectByUserId(Integer userId);
	
	List<IntergralRecords> selectIntergralRecordAll();
	
	List<IntergralRecords> selectIntergralByUserId(Integer userId);
}
