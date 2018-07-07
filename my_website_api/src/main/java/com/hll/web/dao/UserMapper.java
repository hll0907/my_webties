package com.hll.web.dao;

import java.util.List;

import com.hll.web.pojo.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);
	
	User selectByPhone(String phone);

	int updateByPrimaryKeySelective(User record);

	int deleteByPrimaryKey(String user);

	int updateByPrimaryKey(User record);
	
	List<User> selectUserAll();
	
	List<User> selectUserById(Integer id);
}