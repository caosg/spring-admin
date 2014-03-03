/**
 * 商品类目管理前端spring-mvc控制类
 * @author jiaoyuqiang
 * 
 */
package com.hstyle.shop.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.hstyle.shop.model.ProductCategory;
import com.hstyle.shop.service.ProductCategoryService;


@Controller
@RequestMapping("/productcategory")
public class ProductCategoryController extends BaseController{
	
	private static Long ROOT_ID = 0l;
	
	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 根据父类id查询子类(主要用于前台treepanel)
	 * @param id
	 * @return
	 */
	@RequestMapping("children")
    public @ResponseBody Msg getChildren(@RequestParam String id){
    	Long longId = 0L;
		List<ProductCategory> children;
		if (StringUtils.isEmpty(id)) {
			longId = ROOT_ID;
		} else {
			longId = Long.parseLong(id);
		}
		children=productCategoryService.getChildren(longId);
		Msg msg = new Msg(true,children);
		return msg;
    }
	/**
	 * 根据父类id查询子类(主要用于前台treepanel) 公共使用，不记录在访问日志中
	 * @param id
	 * @return
	 */
	@RequestMapping("commonChildren")
    public @ResponseBody Msg getCommonChildren(@RequestParam String id){
    	Long longId = 0L;
		List<ProductCategory> children;
		if (StringUtils.isEmpty(id)) {
			longId = ROOT_ID;
		} else {
			longId = Long.parseLong(id);
		}
		children=productCategoryService.getChildren(longId);
		Msg msg = new Msg(true,children);
		return msg;
    }
	/**
	 * 根据父类的code模糊查询所有的子类信息 
	 * 1:分页
	 * @param request
	 * @param code
	 * @return
	 */
	@RequestMapping("query")
	public @ResponseBody Page<ProductCategory> queryChildren(HttpServletRequest request , String code){
		PageRequest pageRequest=buildPageRequest(request);//分页参数
		List<PropertyFilter> filters=buildFilterProperties(request);
		PropertyFilter cateFilter=new PropertyFilter("EQ",PropertyType.S,new String[]{"status"},"1");
		filters.add(cateFilter);
		Page<ProductCategory> productCategory =  productCategoryService.findPage(pageRequest, filters);
		return productCategory;
	} 
	
	
	@RequestMapping("getAllChildren")
	public @ResponseBody Msg getAllChildren(@RequestParam(required=true) String code){
		return success(productCategoryService.getAllChildren(code));
	} 	
	/**
	 * @category 获取所有类目信息 改url不记录到系统访问日志中
	 * @param code
	 * @return
	 */
	@RequestMapping("commonAllChildren")
	public @ResponseBody Msg getCommonAllChildren(@RequestParam(required=true) String code){
		return success(productCategoryService.getAllChildren(code));
	} 	
	/**
	 * 更新或者新增类目
	 * @param productCategory
	 * @return
	 */
    @RequestMapping("edit")
	public @ResponseBody Msg edit(@Valid ProductCategory productCategory) {
    	logger.info("save ProductCategory --");
    	if(productCategory.getId()!=null)
    		productCategoryService.update(productCategory);
    	else
    		productCategoryService.saveProductCategory(productCategory);
    	Msg msg = new Msg(true,productCategory);
		return msg;
	}	
    
    /**
     * 删除类目
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public @ResponseBody Msg delete(@RequestParam String id){
    	Long longId = 0L;
		if (StringUtils.isEmpty(id)) {
			longId = ROOT_ID;
		} else {
			longId = Long.parseLong(id);
		}
		logger.info("delete ProductCategory id:{}",id);
		if(longId!=ROOT_ID)
			productCategoryService.delete(longId);
		Msg msg = new Msg(true,"删除成功");
		return msg;
    }    
}
