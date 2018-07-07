package com.hll.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hll.web.pojo.User;
import com.hll.web.result.ResultMsg;
import com.hll.web.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	
	@Autowired 
	UserService userService;
	private static Logger logger = Logger.getLogger(UserController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login(ModelMap model, HttpServletRequest request) {
		logger.error(ResultMsg.failure());
		List<User> users = null;
		users = userService.selectUserAll();
		System.out.println(ResultMsg.success(users));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg login(HttpServletRequest request, HttpServletResponse response) {
		logger.fatal(ResultMsg.failure("1", "2", 0));
		return null;
	}
}
