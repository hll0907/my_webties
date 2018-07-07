package com.hll.web.dao;

import com.hll.web.pojo.IntergralRecords;

public interface IntergralRecordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IntergralRecords record);

    int insertSelective(IntergralRecords record);

    IntergralRecords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IntergralRecords record);

    int updateByPrimaryKey(IntergralRecords record);
}