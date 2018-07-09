package com.hll.web.operatelog;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hll.web.pojo.OperateLog;
import com.hll.web.pojo.User;
import com.hll.web.service.OperateLogService;
import com.hll.web.util.DateTimeUtil;

@Aspect
@Component
public class AccountSysOptionLogAspect {
	static User anyuser = new User();
	static {
		anyuser.setId(999);
	}
	@Autowired
	OperateLogService operateLogService;

	@Pointcut("@annotation(com.hll.web.operatelog.OperateLogs)")
	public void controllerAspecta() {
	}

	@AfterReturning(value = "controllerAspecta() && @annotation(annotation) &&args(object,..) ", argNames = "annotation,object")
	public void interceptorApplogicA(OperateLogs annotation, Object object) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		User user = (User) request.getSession().getAttribute("userdata");
		if (user == null) {
			user = anyuser;
		}
		OperateLog operateLog = new OperateLog();
		operateLog.setUserid(user.getId());
		operateLog.setModulename(annotation.moduleName());
		operateLog.setOperatedesc(annotation.option());
		operateLog.setIpaddress(getIpAddr(request));
		operateLog.setCreatetime(DateTimeUtil.getStringDate());
		operateLog.setResult(annotation.url());
		int insertSelective = operateLogService.insertSelective(operateLog);
		if (insertSelective > 0) {
			System.out.println("成功");
		} else {
			System.err.println("失败");
		}
	}

	/**
	 * 获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
