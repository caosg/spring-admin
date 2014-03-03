package com.hstyle.admin.web.fillter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hstyle.admin.common.UserContext;
import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.model.OperationLog;
import com.hstyle.admin.service.support.SystemUtils;
import com.hstyle.admin.web.utils.LogQueue;

public class SysLogFilter implements Filter {
	private Logger log = LoggerFactory.getLogger(getClass());
	private String[] excludeUrls = { "index.jsp", "/login.do", "/main.do",
			"/logout.do" };

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String url = request.getRequestURI();
		log.info("request url:{}", url);
		boolean logFlag = true;
		for (String exclueUrl : excludeUrls) {
			if (url.contains(exclueUrl))
				logFlag = false;
		}
		UserContext userContext=SystemUtils.getUserContext();
		if (userContext == null || userContext.getOpts() == null) {
			
			logFlag = false;
		}
		
		
		
		if (logFlag) {
			
			List<FunctionPermission> funtions = userContext.getOpts();
			DateTimeFormatter fmt = DateTimeFormat
					.forPattern("yyyy-MM-dd HH:mm:ss");

			DateTime curTime = new DateTime();
			String curTimeString = curTime.toString(fmt);
			for (FunctionPermission func : funtions) {
				
				// 请求url是菜单功能操作，则记录请求日志
				if (url.contains(func.getUrl())) {
					OperationLog operationLog = new OperationLog();
					log.info("operation:{}--{}--{}--{}--{}", func.getId(), request
							.getSession().getId(), userContext.getUser()
							.getId(), curTimeString,curTime.getYear());
					operationLog.setFunctionId(func.getId());
					operationLog.setUserId(userContext.getUser().getId());
					operationLog.setSessionId(request.getSession().getId());
					operationLog.setYear(curTime.getYear());
					operationLog.setMonth(curTime.getMonthOfYear());
					operationLog.setWeek(curTime.getWeekOfWeekyear());
					operationLog.setDayOfMonth(curTime.getDayOfMonth());
					operationLog.setVisiteDate(new Date());
					LogQueue.add(operationLog);
				}
			}
		}
		
		chain.doFilter(arg0, arg1);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
