package com.hll.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hll.web.result.ResultMsg;
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public void index(ModelMap model, HttpServletRequest request) {

	}
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	@ResponseBody
	public ResultMsg index(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}
}
