package com.hll.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hll.web.dao.IntergralRecordsMapper;
import com.hll.web.dao.UserMapper;
import com.hll.web.pojo.IntergralRecords;
import com.hll.web.pojo.User;
import com.hll.web.service.UserService;
import com.hll.web.util.DateTimeUtil;
import com.hll.web.util.PwdUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	IntergralRecordsMapper intergralRecordsMapper;
	@Override
	public List<User> selectUserAll() {
		List<User> uList = userMapper.selectUserAll();
		if (uList.isEmpty()) {
			return null;
		}
		return uList;
	}

	@Override
	public List<User> selectUserById(Integer id) {
		List<User> selectUserById = userMapper.selectUserById(id);
		if (selectUserById.isEmpty()) {
			return null;
		}
		return selectUserById;
	}

	@Transactional
	@Override
	public int insertSelective(User record) {
		User user = userMapper.selectByPhone(record.getPhone());
		if (user != null) {
			return 0;
		}
		User user2 = new User();
		String tempSalt = PwdUtil.getSalt();
		user2.setSalt(tempSalt);
		user2.setPhone(record.getPhone());
		user2.setNickname(record.getNickname());
		user2.setHashPassword(PwdUtil.getHashPassword(tempSalt, record.getHashPassword()));
		user2.setHeadPic(record.getHeadPic());
		int insertSelective = userMapper.insertSelective(user2);
		if (insertSelective > 0) {
			IntergralRecords intergralrecord = new IntergralRecords();
			intergralrecord.setTempIntergral(100);
			intergralrecord.setIntergral(100);
			intergralrecord.setUserId(user2.getId());
			intergralrecord.setSource("注册");
			intergralrecord.setNote("注册奖励");
			String createdAt = DateTimeUtil.getStringDate();
			intergralrecord.setCreatedAt(createdAt);
			intergralrecord.setUpdatedAt(createdAt);
			intergralrecord.setIntergralType(1);
			intergralRecordsMapper.insert(intergralrecord);
			return 1;
		}
		return 0;
	}

	@Override
	public User selectByPhone(String phone) {
		User user = userMapper.selectByPhone(phone);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	public User userLogin(User user, String password) {
		String salt = user.getSalt();
		String userpassword = PwdUtil.getHashPassword(salt, password);
		if (PwdUtil.pwdCompare(userpassword, user.getHashPassword())) {
			saveUser(user);
			return user;
		}
		return null;
	}

	@Transactional
	@Override
	public int saveUser(User user) {
		String lastVisitTime = DateTimeUtil.getStringDate();
		user.setLastVisitTime(lastVisitTime);
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Transactional
	@Override
	public int updateByPrimaryKeySelective(User record) {
		String updateTime = DateTimeUtil.getStringDate();
		record.setUpdateTime(updateTime);
		record.setLastVisitTime(updateTime);
		int i = userMapper.updateByPrimaryKeySelective(record);
		if (i > 0) {
			return i;
		}
		return 0;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Integer id) {
		int i = userMapper.deleteByPrimaryKey(id);
		if (i > 0) {
			return i;
		}
		return 0;
	}

}
