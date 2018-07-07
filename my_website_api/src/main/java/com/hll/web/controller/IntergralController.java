package com.hll.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
}
