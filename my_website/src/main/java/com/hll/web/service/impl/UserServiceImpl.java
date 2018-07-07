package com.hll.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hll.web.dao.UserMapper;
import com.hll.web.pojo.User;
import com.hll.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Override
	public List<User> selectUserAll() {
		List<User> uList = userMapper.selectUserAll();
		if (uList.isEmpty()) {
			return null;
		}
		return uList;
	}

}
