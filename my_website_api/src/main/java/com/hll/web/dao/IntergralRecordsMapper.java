package com.hll.web.dao;

import java.util.List;

import com.hll.web.pojo.IntergralRecords;

public interface IntergralRecordsMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(IntergralRecords record);

	int insertSelective(IntergralRecords record);

	IntergralRecords selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(IntergralRecords record);

	int updateByPrimaryKey(IntergralRecords record);

	IntergralRecords selectByUserId(Integer userId);

	List<IntergralRecords> selectIntergralRecordAll();
	
	List<IntergralRecords> selectIntergralByUserId(Integer userId);
}