package com.hll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hll.web.operatelog.OperateLogs;
import com.hll.web.pojo.IntergralRecords;
import com.hll.web.pojo.User;
import com.hll.web.result.ResultMsg;
import com.hll.web.service.IntergralService;
import com.hll.web.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/intergral")
public class IntergralController {

	@Autowired
	IntergralService intergralService;
	@Autowired
	UserService userService;

	/**
	 * 查询所有用户/根据用户Id积分记录
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@OperateLogs(moduleName = "查询所有用户/根据用户Id积分记录", option = "查询所有用户/根据用户Id积分记录", url = "/IntergralController/selectIntegraldata")
	@RequestMapping(value = "/selectIntegraldata", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "查询所有用户/根据用户Id积分记录", notes = "查询所有用户/根据用户Id积分记录")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页返回个数,默认10条数据", paramType = "query") })
	public ResultMsg selectIntegraldata(Integer userId, Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<IntergralRecords> selectIntergralRecordAll = null;
		if (userId == null) {
			selectIntergralRecordAll = intergralService.selectIntergralRecordAll();
		} else {
			selectIntergralRecordAll = intergralService.selectIntergralByUserId(userId);
		}
		if (selectIntergralRecordAll == null) {
			return ResultMsg.failure("暂无记录", null, 0);
		}
		PageInfo<IntergralRecords> uPageInfo = new PageInfo<IntergralRecords>(selectIntergralRecordAll);
		return ResultMsg.success(uPageInfo);
	}

	@OperateLogs(moduleName = "添加积分", option = "添加积分", url = "/IntergralController/addUserIntergral")
	@RequestMapping(value = "/addUserIntergral", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "添加积分", notes = "添加积分")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "tempIntergral", value = "积分", required = true, paramType = "query"),
			@ApiImplicitParam(name = "source", value = "积分来源", paramType = "query"),
			@ApiImplicitParam(name = "note", value = "详细描述", paramType = "query") })
	public ResultMsg addUserIntergral(Integer userId, Integer tempIntergral, String source, String note) {
		List<User> selectUserById = userService.selectUserById(userId);
		if (selectUserById == null) {
			return ResultMsg.failure("无此用户", null, -1);
		}
		User user = selectUserById.get(0);
		Integer integral = user.getIntegral();
		IntergralRecords intergralRecords = new IntergralRecords();
		intergralRecords.setUserId(userId);
		intergralRecords.setTempIntergral(tempIntergral);
		intergralRecords.setIntergral(tempIntergral + integral);
		intergralRecords.setSource(source);
		intergralRecords.setNote(note);
		int i = intergralService.insert(intergralRecords);
		if (i > 0) {
			User record = new User();
			record.setId(userId);
			record.setIntegral(tempIntergral + integral);
			userService.updateByPrimaryKeySelective(record);
			return ResultMsg.success("添加成功");
		}
		return ResultMsg.failure();
	}

	@OperateLogs(moduleName = "扣除积分", option = "扣除积分", url = "/IntergralController/delUserIntergral")
	@RequestMapping(value = "/delUserIntergral", method = RequestMethod.PUT, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "扣除积分", notes = "扣除积分")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "tempIntergral", value = "积分", required = true, paramType = "query"),
			@ApiImplicitParam(name = "source", value = "积分来源", paramType = "query"),
			@ApiImplicitParam(name = "note", value = "详细描述", paramType = "query") })
	public ResultMsg delUserIntergral(Integer userId, Integer tempIntergral, String source, String note) {
		List<User> selectUserById = userService.selectUserById(userId);
		User user = selectUserById.get(0);
		Integer integral = user.getIntegral();
		IntergralRecords intergralRecords = new IntergralRecords();
		intergralRecords.setUserId(userId);
		intergralRecords.setTempIntergral(tempIntergral);
		intergralRecords.setIntergral(integral - tempIntergral);
		intergralRecords.setSource(source);
		intergralRecords.setNote(note);
		intergralRecords.setIntergralType(0);
		int i = intergralService.insert(intergralRecords);
		if (i > 0) {
			User record = new User();
			record.setId(userId);
			record.setIntegral(integral - tempIntergral);
			userService.updateByPrimaryKeySelective(record);
			return ResultMsg.success("扣除成功");
		}
		return ResultMsg.failure();
	}

	@OperateLogs(moduleName = "修改积分", option = "修改积分", url = "/IntergralController/updateUserIntergral")
	@RequestMapping(value = "/updateUserIntergral", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "修改积分", notes = "修改积分")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "积分记录Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "tempIntergral", value = "积分", required = true, paramType = "query"),
			@ApiImplicitParam(name = "source", value = "积分来源", paramType = "query"),
			@ApiImplicitParam(name = "note", value = "详细描述", paramType = "query"),
			@ApiImplicitParam(name = "types", value = "操作类型(默认true加)", required = true, paramType = "query", dataType = "boolean") })
	public ResultMsg updateUserIntergral(Integer id, Integer userId, Integer tempIntergral, String source, String note,
			boolean types) {
		IntergralRecords selectByPrimaryKey = intergralService.selectByPrimaryKey(id);
		if (selectByPrimaryKey == null) {
			return ResultMsg.failure("暂无积分记录", null, -1);
		}
		if (!userId.equals(selectByPrimaryKey.getUserId())) {
			return ResultMsg.failure("无此用户积分记录", null, -2);
		}
		IntergralRecords intergralRecords = new IntergralRecords();
		intergralRecords.setId(selectByPrimaryKey.getId());
		intergralRecords.setUserId(userId);
		intergralRecords.setTempIntergral(tempIntergral);
		List<User> selectUserById = userService.selectUserById(userId);
		User user = selectUserById.get(0);
		if (types) {
			intergralRecords.setIntergral(user.getIntegral() + tempIntergral);
			intergralRecords.setIntergralType(1);
		} else {
			intergralRecords.setIntergral(user.getIntegral() - tempIntergral);
			intergralRecords.setIntergralType(0);
		}
		intergralRecords.setSource(source);
		intergralRecords.setNote(note);
		int updateByPrimaryKeySelective = intergralService.updateByPrimaryKeySelective(intergralRecords);
		if (updateByPrimaryKeySelective > 0) {
			return ResultMsg.success("修改成功");
		}
		return ResultMsg.failure();
	}

	@OperateLogs(moduleName = "删除积分记录", option = "删除积分记录,并非真的删除,把status改为0", url = "/IntergralController/delUserIntergraldata")
	@RequestMapping(value = "/delUserIntergraldata", method = RequestMethod.DELETE, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "删除积分记录", notes = "删除积分记录,并非真的删除,把status改为0")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "积分记录Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query") })
	public ResultMsg delUserIntergraldata(Integer id, Integer userId) {
		IntergralRecords selectByPrimaryKey = intergralService.selectByPrimaryKey(id);
		if (selectByPrimaryKey == null) {
			return ResultMsg.failure("暂无积分记录", null, -1);
		}
		if (!userId.equals(selectByPrimaryKey.getUserId())) {
			return ResultMsg.failure("无此用户积分记录", null, -2);
		}
		IntergralRecords record = new IntergralRecords();
		record.setId(id);
		record.setStatus(false);
		int updateByPrimaryKeySelective = intergralService.updateByPrimaryKeySelective(record);
		if (updateByPrimaryKeySelective > 0) {
			return ResultMsg.success("成功");
		}
		return ResultMsg.failure();
	}
}
