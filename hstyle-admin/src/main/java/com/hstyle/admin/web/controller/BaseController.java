/**
 * 
 */
package com.hstyle.admin.web.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.hstyle.admin.web.utils.AjaxUtils;
import com.hstyle.admin.web.utils.PropertyFilterRestrictionWeb;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.excel.exp.module.ExcelModule;
import com.hstyle.framework.excel.exp.service.ExcelExpUtil;
import com.hstyle.framework.exception.ApplicationException;

/**
 * 基础spring-mvc控制类，封装常用函数
 * 
 * @author Administrator
 * 
 */
public abstract class BaseController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static String DEFAULT_PATH="/templates/";//excel模板文件默认路径

	/**
	 * 生成分页参数信息,包括排序信息
	 * 
	 * @param request
	 * @return
	 */
	protected PageRequest buildPageRequest(HttpServletRequest request) {
		PageRequest pageRequest = new PageRequest();
		// 分页参数
		String page = request.getParameter("page");
		String limit = request.getParameter("limit");
		Assert.notNull(page, "page must not be null!");
		Assert.notNull(limit,"limit must not be null!");
		pageRequest.setPageNo(Integer.parseInt(page));
		pageRequest.setPageSize(Integer.parseInt(limit));
		String sortJson=request.getParameter("sort");
		logger.info("page request pageNo:{} - limit:{} - sort:{}",page,limit,sortJson);
		if(StringUtils.isNotBlank(sortJson)){
			pageRequest.setSortJson(sortJson);
		}
		return pageRequest;
	}
	/**
	 * 构造查询条件参数，默认以filter_开头
     * 例如：页面提交的参数为:filter_EQ_S_property1=value&filter_EQ_S_property2=value2
	 * List filters =buildPropertyFilter(request,"filter",false);
	 * 当前filters:EQ_S_proerpty1="value",EQ_S_proerpty1="value2"
	 * @param request
	 * @return PropertyFilter
	 */
	protected List<PropertyFilter> buildFilterProperties(HttpServletRequest request){
		return PropertyFilterRestrictionWeb.buildFromHttpRequest(request);
	}
	/**
	 * 返回成功信息
	 * @param data 传递给前端的数据
	 * @return msg
	 */
	protected Msg success() {
		return new Msg(true,"操作成功");
	}
	/**
	 * 返回成功信息
	 * @param data 传递给前端的数据
	 * @return msg
	 */
	protected Msg success(Object data) {
		return new Msg(true,data);
	}
	/**
	 * 返回错误提示信息
	 * @param data 传递给前端的数据
	 * @return msg
	 */
	protected Msg fail(Object data) {
		
		return new Msg(false,data);
	}
	
	/**
	 * 判断http请求是否是ajax请求
	 * @param request
	 * @param model
	 */
	@ModelAttribute
	public void ajaxAttribute(WebRequest request, Model model) {
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
	}
	
	/**
	 * 业务异常统一处理，
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	public @ResponseBody Msg handle(ApplicationException e) {
		return fail("错误："+e.getMessage());
	}
	
	/**
	 * 系统异常统一处理，
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	public @ResponseBody Msg handle(RuntimeException e) {
        e.printStackTrace();
		return fail("系统异常");
	}
	
	/**
	 * 导出Excel
	 * @param excelModule Excel数据对象
	 * @param response  输出流
	 * @param templateName Excel模板文件名
	 * @param fileName 下载文件名
	 */
	@Deprecated
	public void exportExcel(ExcelModule excelModule,HttpServletResponse response,String templateName,String fileName){
		response.setContentType("application/vnd.ms-excel;charset=GBK");  
		response.setHeader("Content-Disposition", "attachment; filename="+fileName); 
		try {
						
			ExcelExpUtil.expExcel(excelModule, templateName,response.getOutputStream());
		} catch (Exception e) {
			logger.error("export excel:{} is faile.",templateName);
			e.printStackTrace();
		}
	}
	
	/**
	 * 基于xls模板导出excel到 httpresponse 输出流
	 * @param data  Beans in a map under keys used in .xls template to access to the beans
	 * @param templateName the excel template file's name
	 * @param response Http response 
	 */
	public void expExcel(Map data,String templateName,HttpServletResponse response){
		this.expExcel(data, templateName, templateName, response);
	}
	
	/**
	 * 基于xls模板导出excel到 httpresponse 输出流
	 * @param data  Beans in a map under keys used in .xls template to access to the beans
	 * @param templateName the excel template file's name
	 * @param expFileName  the export file's name
	 * @param response Http response 
	 */
	public void expExcel(Map data,String templateName,String expFileName,HttpServletResponse response){
		XLSTransformer transformer = new XLSTransformer();
		response.setContentType("application/vnd.ms-excel;charset=GBK");  
		response.setHeader("Content-Disposition", "attachment; filename="+expFileName);
		InputStream is;
		try {
		    is = new BufferedInputStream(new FileInputStream(this.getClass().getResource(DEFAULT_PATH+templateName).getFile()));
			transformer.transformXLS(is,data).write(response.getOutputStream());
			is.close();
		} catch (Exception e) {
			logger.error("export excel:{} is faile.",templateName);
			e.printStackTrace();
		}
	 }
	/**
	 * 基于xls模板导出excel到 指定目录文件
	 * @param data  Beans in a map under keys used in .xls template to access to the beans
	 * @param templateFileName  the excle .xls template file
	 * @param destFilePath  Path to result .xls file
	 */
	public void expExcel(Map data,String templateFileName, String destFilePath){
		XLSTransformer transformer = new XLSTransformer();
		try {	
			String templateAbsolutePath=this.getClass().getResource(DEFAULT_PATH).getFile();
			transformer.transformXLS(templateAbsolutePath+templateFileName, data, destFilePath);
		} catch (Exception e) {
			logger.error("export excel:{} is faile.",templateFileName);
			e.printStackTrace();
		}
	}
}
