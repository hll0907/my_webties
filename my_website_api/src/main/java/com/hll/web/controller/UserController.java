package com.hll.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hll.web.operatelog.OperateLogs;
import com.hll.web.pojo.User;
import com.hll.web.result.ResultMsg;
import com.hll.web.service.IntergralService;
import com.hll.web.service.UserService;
import com.hll.web.util.CommUtil;
import com.hll.web.util.PwdUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	IntergralService intergralService;

	/**
	 * 查询所有用户
	 *  
	 * @param pageNo 页码
	 * @param pageSize 一页显示条数
	 * @return
	 */
	@OperateLogs(moduleName = "查询用户", option = "查询用户信息", url = "/UserController/selectUserdata")
	@RequestMapping(value = "/selectUserdata", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "查询所有的用户信息", notes = "查询所有的用户信息")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "pageNo", value = "页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页返回个数,默认10条数据", paramType = "query") })
	public ResultMsg selectUserdata(Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<User> users = userService.selectUserAll();
		if (users == null) {
			return ResultMsg.failure("暂无用户", null, 0);
		}
		PageInfo<User> uPageInfo = new PageInfo<User>(users);
		return ResultMsg.success(uPageInfo);
	}

	/**
	 * 根据用户id查询用户信息
	 * 
	 * @param userId 用户Id
	 * @return
	 */
	@OperateLogs(moduleName = "根据id查询用户", option = "根据id查询用户", url = "/UserController/selectUserByIddata")
	@RequestMapping(value = "/selectUserByIddata", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "根据用户Id查询用户信息", notes = "根据用户Id查询用户信息")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query") })
	public ResultMsg selectUserByIddata(Integer userId) {
		List<User> users = userService.selectUserById(userId);
		if (users == null) {
			return ResultMsg.failure("暂无用户信息", null, 0);
		}
		return ResultMsg.success(users);
	}

	/**
	 * 新增用户
	 * 
	 * @param phone 手机号
	 * @param nickname 昵称
	 * @param password 密码
	 * @param head_pic 头像
	 * @return
	 */
	@OperateLogs(moduleName = "新添加一个用户", option = "新添加一个用户", url = "/UserController/insterUser")
	@RequestMapping(value = "/insterUser", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "新添加一个用户", notes = "新添加一个用户")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "nickname", value = "用户昵称", required = true, paramType = "query"),
			@ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "query"),
			@ApiImplicitParam(name = "head_pic", value = "用户头像", paramType = "form"), })
	public ResultMsg insterUser(String phone, String nickname, String password, String head_pic) {
		User user = new User();
		user.setPhone(phone);
		user.setNickname(nickname);
		user.setHashPassword(password);
		user.setHeadPic(head_pic);
		user.setStatus(true);
		int insertSelective = userService.insertSelective(user);
		if (insertSelective > 0) {
			return ResultMsg.success(user);
		}
		return ResultMsg.failure("该手机号已注册,请直接登录,或联系客服", null, 0);
	}

	/**
	 * 用户登录 判断手机号和密码是否一致
	 * 
	 * @param phone 手机号
	 * @param password 密码
	 * @return
	 */
	@OperateLogs(moduleName = "用户登录", option = "用户登录", url = "/UserController/userLogin")
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ApiOperation(value = "用户登录", notes = "用户登录")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "query"), })
	public ResultMsg userLogin(String phone, String password, HttpSession session) {
		if (phone.isEmpty() || phone.length() < 11) {
			return ResultMsg.failure("手机号格式错误", null, 0);
		}
		User user = userService.selectByPhone(phone);
		if (user == null) {
			return ResultMsg.failure("您还未注册哦", null, -1);
		}
		User userLogin = userService.userLogin(user, password);
		if (userLogin != null) {
			session.setAttribute("userdata", user);
			return ResultMsg.success("登录成功");
		}
		return ResultMsg.failure();
	}

	/**
	 * 修改密码
	 * 
	 * @param phone 手机号
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 * @return
	 */
	@OperateLogs(moduleName = "修改密码", option = "修改密码", url = "/UserController/userUpdatePassword")
	@RequestMapping(value = "/userUpdatePassword", method = RequestMethod.PUT, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "修改密码", notes = "修改密码")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, paramType = "query"),
			@ApiImplicitParam(name = "newPassword", value = "新密码", required = true, paramType = "query") })
	public ResultMsg userUpdatePassword(String phone, String oldPassword, String newPassword) {
		User user = userService.selectByPhone(phone);
		if (user == null) {
			return ResultMsg.failure("用户不存在", null, -1);
		}
		String tempoldpassword = PwdUtil.getHashPassword(user.getSalt(), oldPassword);
		if (!tempoldpassword.equals(user.getHashPassword())) {
			return ResultMsg.failure("原密码错误", null, -2);
		}
		if (oldPassword.equals(newPassword)) {
			return ResultMsg.failure("原密码和新密码不能一样", null, -3);
		}
		user.setHashPassword(PwdUtil.getHashPassword(user.getSalt(), newPassword));
		int i = userService.updateByPrimaryKeySelective(user);
		if (i > 0) {
			return ResultMsg.success(user);
		}
		return ResultMsg.failure();
	}

	/**
	 * 根据id删除用户信息 尽量少用
	 * 
	 * @param userId 用户Id
	 * @return 
	 */
	@OperateLogs(moduleName = "根据用户Id删除用户", option = "根据用户Id删除用户", url = "/UserController/userDeldata")
	@RequestMapping(value = "/userDeldata", method = RequestMethod.DELETE, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "根据用户Id删除用户", notes = "根据用户Id删除用户")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query") })
	public ResultMsg userDeldata(Integer userId) {
		List<User> userById = userService.selectUserById(userId);
		if (userById == null) {
			return ResultMsg.failure("暂无此用户", null, 0);
		}
		int i = userService.deleteByPrimaryKey(userId);
		if (i > 0) {
			System.out.println(i);
			return ResultMsg.success(null);
		}
		return ResultMsg.failure();
	}

	/**
	 * 用户上传头像（图片）
	 * 
	 * @param userId 用户Id
	 * @param headPic 头像
	 * @param request 
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@OperateLogs(moduleName = "上传用户头像", option = "上传用户头像", url = "/UserController/upLoadHeadPic")
	@RequestMapping(value = "/upLoadHeadPic", method = RequestMethod.POST)
	@ApiOperation(value = "上传用户头像", notes = "上传用户头像")
	@ResponseBody
	public ResultMsg upLoadHeadPic(
			@ApiParam(required = true, value = "用户Id") @RequestParam(required = true) Integer userId,
			@ApiParam(required = true, value = "用户头像") @RequestParam(required = true) MultipartFile headPic,
			HttpServletRequest request) throws IllegalStateException, IOException {
		List<User> userById = userService.selectUserById(userId);
		if (userById == null) {
			return ResultMsg.failure("暂无此用户", null, 0);
		}
		// 文件路径
		String path = CommUtil.uploadFile(headPic, request);
		User user = new User();
		user.setId(userId);
		user.setHeadPic(path);
		int i = userService.updateByPrimaryKeySelective(user);
		if (i > 0) {
			return ResultMsg.success(userService.selectUserById(userId));
		}
		return ResultMsg.failure();
	}

	/**
	 * 忘记密码
	 * 
	 * @param phone 手机号
	 * @param actiCode 验证码
	 * @param newPassword 新密码
	 * @return
	 */
	@OperateLogs(moduleName = "忘记密码", option = "忘记密码", url = "/UserController/forgetPassword")
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "忘记密码", notes = "根据邮箱验证码修改密码，code为0，无次用户，code为-1，还未获取验证码，code为-2，验证码错误，code为1，修改成功")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "actiCode", value = "邮箱验证码", required = true, paramType = "query"),
			@ApiImplicitParam(name = "newPassword", value = "新密码", required = true, paramType = "query") })
	public ResultMsg forgetPassword(String phone, String actiCode, String newPassword) {
		User selectByPhone = userService.selectByPhone(phone);
		if (selectByPhone == null) {
			return ResultMsg.failure("暂无此用户", null, 0);
		}
		if (selectByPhone.getActiCode().isEmpty()) {
			return ResultMsg.failure("您还未获取验证码", null, -1);
		}
		if (!selectByPhone.getActiCode().equals(actiCode)) {
			return ResultMsg.failure("验证码错误", null, -2);
		}
		User user = new User();
		user.setId(selectByPhone.getId());
		String hashPassword = PwdUtil.getHashPassword(selectByPhone.getSalt(), newPassword);
		user.setHashPassword(hashPassword);
		int i = userService.updateByPrimaryKeySelective(user);
		if (i > 0) {
			return ResultMsg.success("密码修改成功");
		}
		return ResultMsg.failure();
	}

	/**
	 * 更新会员
	 * @param userId 用户Id
	 * @param newVip_type 会员类型
	 * @return
	 */
	@OperateLogs(moduleName = "更新会员类型", option = "更新会员类型", url = "/UserController/userUpdateVip_type")
	@RequestMapping(value = "/userUpdateVip_type", method = RequestMethod.PUT, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "更新会员类型", notes = "更新会员类型，")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "newVip_type", value = "更新后会员类型", required = true, paramType = "query") })
	public ResultMsg userUpdateVip_type(Integer userId, String newVip_type) {
		List<User> selectUserById = userService.selectUserById(userId);
		if (selectUserById == null) {
			return ResultMsg.failure("无此用户", null, -1);
		}
		User record = new User();
		record.setId(userId);
		record.setVipType(newVip_type);
		int i = userService.updateByPrimaryKeySelective(record);
		if (i > 0) {
			return ResultMsg.success("更新会员类型成功");
		}
		return ResultMsg.failure();
	}
}
