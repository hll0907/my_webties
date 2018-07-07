package com.hll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hll.web.canstants.Global;
import com.hll.web.pojo.User;
import com.hll.web.result.ResultMsg;
import com.hll.web.service.MailService;
import com.hll.web.service.UserService;
import com.hll.web.util.UUIDUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	MailService mailService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/sendmail", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "邮箱发送验证码", notes = "邮箱发送验证码")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "usermail", value = "用户邮箱", required = true, paramType = "query") })
	public ResultMsg sendmail(Integer userId, String usermail) {
		List<User> userById = userService.selectUserById(userId);
		if (userById == null) {
			return ResultMsg.failure("暂无此用户", null, 0);
		}
		// 邮件主题
		String subject = Global.mailSubject;
		// 验证码
		String msgBody = UUIDUtil.getUUID().substring(0, 6);
		boolean sendEmail = mailService.sendEmail(usermail, subject, msgBody);
		if (sendEmail) {
			User record = new User();
			record.setId(userId);
			record.setActiCode(msgBody);
			int i = userService.updateByPrimaryKeySelective(record);
			if (i > 0) {
				return ResultMsg.success("邮件发送成功，请注意查收");
			}
		}
		return ResultMsg.failure();
	}
}