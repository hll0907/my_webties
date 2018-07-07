package com.hll.web.service;

import java.util.List;

import com.hll.web.pojo.User;

public interface UserService {

	int insertSelective(User record);

	List<User> selectUserAll();

	List<User> selectUserById(Integer id);

	User selectByPhone(String phone);

	User userLogin(User user, String password);

	int saveUser(User user);

	int updateByPrimaryKeySelective(User record);

	int deleteByPrimaryKey(Integer id);
	
}
