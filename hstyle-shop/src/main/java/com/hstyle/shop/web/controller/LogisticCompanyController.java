package com.hstyle.shop.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.shop.model.LogisticCompany;
import com.hstyle.shop.service.LogisticCompanyService;
@Controller
@RequestMapping("/lgcompany")
public class LogisticCompanyController extends BaseController{
	@Autowired
	private LogisticCompanyService logisticCompanyService;
	
	/**
	 * 查询物流公司
	 * @param request
	 * @return
	 */
	@RequestMapping("listCompany")
	public @ResponseBody Page<LogisticCompany> listLogisticCompany(HttpServletRequest request){
		PageRequest pageRequest=buildPageRequest(request);//分页参数
		List<PropertyFilter> filters=buildFilterProperties(request);
    	PropertyFilter deptFilter=new PropertyFilter("EQ",PropertyType.S,new String[]{"status"},"1");
    	filters.add(deptFilter);
		Page<LogisticCompany> LogisticCompany =  logisticCompanyService.findPage(pageRequest, filters);
		return LogisticCompany;
	}
	
	/**
	 * 更新物流公司
	 * @param LogisticCompany
	 * @return
	 */
	@RequestMapping("updateCompany")
	public @ResponseBody Msg updateLogisticCompany(@Valid LogisticCompany LogisticCompany){
		LogisticCompany.setStatus("1");
		logisticCompanyService.save(LogisticCompany);
		return success(LogisticCompany);
	}
	
	/**
	 * 删除物流公司
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteCompany")
	public @ResponseBody Msg deleteLogisticCompany(@RequestParam("ids")List<Long> ids){
		logisticCompanyService.deleteAll(ids);
		return success(true);
	}
}
