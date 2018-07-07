package com.hll.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hll.web.result.ResultMsg;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/weChatUser")
public class WeChatController {

	@RequestMapping(value = "/weChatLogin", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "微信授权登录", notes = "微信授权登录")
	@ResponseBody
	public ResultMsg weChatLogin() {
		String backUrl = "";
		String url = "";
		
		return null;
	}
}
