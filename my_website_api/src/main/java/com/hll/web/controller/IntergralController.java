package com.hll.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hll.web.result.ResultMsg;
import com.hll.web.service.UserService;

@Controller
@RequestMapping("/intergral")
public class IntergralController {

	@Autowired
	UserService userService;

	public ResultMsg updateUserIntergral(Integer userId, Integer intergral) {
		
		return null;
	}
}
