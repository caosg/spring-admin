/**
 * 
 */
package com.hstyle.admin.web.controller.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hstyle.admin.model.OperationLog;
import com.hstyle.admin.service.OperationLogService;
import com.hstyle.admin.service.support.SystemUtils;
import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;

/**
 * 操作日志管理spring-mvc前端控制类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/operl")
public class OperationLogController extends BaseController{
	
	@Autowired
	private OperationLogService operationLogService;
	
	/**
	 * 保存操作日志
	 * @param log
	 * @return
	 */
	@RequestMapping("save")
	public @ResponseBody Msg saveLog(OperationLog log , HttpServletRequest request){
		logger.info("excute saveLog method......");
		log.setSessionId(request.getSession().getId());
		log.setUserId(SystemUtils.getUserContext().getUser().getId());
		log.setVisiteDate(new Date());
		operationLogService.add(log);
		return null;
	}
	/**
	 * 统计系统访问次数
	 * @param type hour:小时统计 day：按天统计 week：按周统计
	 * @return
	 */
	@RequestMapping("statvisit")
	public @ResponseBody Msg statVisists(String type){
		List result=null;
		if(StringUtils.isEmpty(type))
			type="hour";
		Map<Integer, List> map=operationLogService.statSysLog(type);
		if(map!=null)
		   result=map.get(0);
		logger.info("stat result:{}",result.size());
		return success(result);
	}
	/**
	 * 操作功能按点击数排行前10名
	 * @param limit 
	 * @return
	 */
	@RequestMapping("statoperation")
	public @ResponseBody Msg statOperationRank(int limit){
		if(limit==0)
			limit=20;
		List result=operationLogService.statSysOperationRank(limit);		
		logger.info("limit:{},statoperation result:{}",limit,result.size());
		return success(result);
	}
	/**
	 * 统计操作总点击量
	 * @param type hour:小时统计 day：按天统计 week：按周统计
	 * @return
	 */
	@RequestMapping("stathits")
	public @ResponseBody Msg statHits(String type){
		if(StringUtils.isEmpty(type))
			type="hour";
		List result=operationLogService.statHits(type);		
		logger.info("stathits result:{}",result.size());
		return success(result);
	}
}
