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
import com.hll.web.pojo.OperateLog;
import com.hll.web.pojo.User;
import com.hll.web.result.ResultMsg;
import com.hll.web.service.OperateLogService;
import com.hll.web.service.UserService;
import com.hll.web.util.DateTimeUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/operatelog")
public class OperateLogController {

	@Autowired
	OperateLogService operateLogService;
	@Autowired
	UserService userService;

	@OperateLogs(moduleName = "查询操作日志", option = "查询所以用户/根据用户Id查询操作日志记录", url = "/OperateLogController/selectOperateLog")
	@RequestMapping(value = "/selectOperateLog", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "查询操作日志", notes = "查询所以用户/根据用户Id查询操作日志记录")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", paramType = "query"),
			@ApiImplicitParam(name = "pageNo", value = "页码", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页返回个数,默认10条数据", paramType = "query") })
	public ResultMsg selectOperateLog(Integer userId, Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		List<OperateLog> operateLogs = null;
		if (userId == null) {
			operateLogs = operateLogService.selectAll();
		} else {
			operateLogs = operateLogService.selectByUserId(userId);
		}
		if (operateLogs.isEmpty()) {
			return ResultMsg.failure("暂无此用户的操作记录", null, 0);
		}
		PageInfo<OperateLog> oPageInfo = new PageInfo<OperateLog>(operateLogs);
		return ResultMsg.success(oPageInfo);
	}

	@OperateLogs(moduleName = "增加操作日志", option = "增加操作日志记录", url = "/OperateLogController/selectOperateLog")
	@RequestMapping(value = "/insertOperateLog", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "增加操作日志", notes = "增加操作日志")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "modulename", value = "操作模块", paramType = "query"),
			@ApiImplicitParam(name = "operatedesc", value = "操作描述", paramType = "query"),
			@ApiImplicitParam(name = "ipaddress", value = "操作Ip", paramType = "query"),
			@ApiImplicitParam(name = "result", value = "备注", paramType = "query") })
	public ResultMsg insertOperateLog(Integer userId, String modulename, String operatedesc, String ipaddress,
			String result) {
		List<User> selectUserById = userService.selectUserById(userId);
		if (selectUserById == null) {
			return ResultMsg.failure("无此用户Id", null, -1);
		}
		OperateLog record = new OperateLog();
		record.setUserid(userId);
		record.setModulename(modulename);
		record.setOperatedesc(operatedesc);
		record.setIpaddress(ipaddress);
		record.setResult(result);
		int i = operateLogService.insertSelective(record);
		if (i > 0) {
			return ResultMsg.success("添加成功");
		}
		return ResultMsg.failure();
	}

	@OperateLogs(moduleName = "更新操作日志", option = "更新操作日志记录", url = "/OperateLogController/updateOperateLog")
	@RequestMapping(value = "/updateOperateLog", method = RequestMethod.PUT, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "更新操作日志", notes = "更新操作日志记录")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "记录Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "userId", value = "用户Id", required = true, paramType = "query"),
			@ApiImplicitParam(name = "modulename", value = "操作模块", required = true, paramType = "query"),
			@ApiImplicitParam(name = "operatedesc", value = "操作描述", required = true, paramType = "query"),
			@ApiImplicitParam(name = "ipaddress", value = "操作Ip", required = true, paramType = "query"),
			@ApiImplicitParam(name = "result", value = "备注", required = true, paramType = "query") })
	public ResultMsg updateOperateLog(Integer id, Integer userId, String modulename, String operatedesc,
			String ipaddress, String result) {
		OperateLog selectByPrimaryKey = operateLogService.selectByPrimaryKey(id);
		if (selectByPrimaryKey == null) {
			return ResultMsg.failure("无此操作记录信息", null, -1);
		}
		List<User> selectUserById = userService.selectUserById(userId);
		if (selectUserById == null) {
			return ResultMsg.failure("无此用户Id", null, -2);
		}
		OperateLog record = new OperateLog();
		record.setId(selectByPrimaryKey.getId());
		record.setUserid(userId);
		record.setModulename(modulename);
		record.setOperatedesc(operatedesc);
		record.setIpaddress(ipaddress);
		record.setResult(result);
		record.setCreatetime(DateTimeUtil.getStringDate());
		int updateByPrimaryKeySelective = operateLogService.updateByPrimaryKeySelective(record);
		if (updateByPrimaryKeySelective > 0) {
			return ResultMsg.success("更新成功");
		}
		return ResultMsg.failure("更新失败", null, 0);
	}

	@OperateLogs(moduleName = "删除操作日志", option = "删除操作日志记录", url = "/OperateLogController/delOperateLog")
	@RequestMapping(value = "/delOperateLog", method = RequestMethod.DELETE, produces = {
			"application/json;charset=UTF-8" })
	@ApiOperation(value = "删除操作日志", notes = "删除操作日志记录")
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "记录Id", required = true, paramType = "query") })
	public ResultMsg delOperateLog(Integer id) {
		OperateLog selectByPrimaryKey = operateLogService.selectByPrimaryKey(id);
		if (selectByPrimaryKey == null) {
			return ResultMsg.failure("无此操作记录信息", null, -1);
		}
		int deleteByPrimaryKey = operateLogService.deleteByPrimaryKey(id);
		if (deleteByPrimaryKey > 0) {
			return ResultMsg.success("操作记录删除成功");
		}
		return ResultMsg.failure("删除失败", null, 0);
	}

}
