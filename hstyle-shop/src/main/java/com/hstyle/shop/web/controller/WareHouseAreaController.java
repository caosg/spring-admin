/**
 * 仓库管理spring-mvc前端控制类
 * @author Administrator
 *
 */
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
import com.hstyle.shop.model.WareHouse;
import com.hstyle.shop.model.WareHouseArea;
import com.hstyle.shop.service.WareHouseAreaService;


@Controller
@RequestMapping("/warehouse")
public class WareHouseAreaController extends BaseController{
	@Autowired
	private WareHouseAreaService wareHouseAreaService;
	private static final String COMMON_YES = "1";

	/**
	 * 查询仓库区域
	 * @param request
	 * @return
	 */
	@RequestMapping("listWareHouseArea")
	public @ResponseBody Page<WareHouseArea> listWareHouseArea(HttpServletRequest request){
		PageRequest pageRequest=buildPageRequest(request);//分页参数
		List<PropertyFilter> filters=buildFilterProperties(request);
    	PropertyFilter areaFilter=new PropertyFilter("EQ",PropertyType.S,new String[]{"status"},"1");
    	filters.add(areaFilter);
		Page<WareHouseArea> area =  wareHouseAreaService.findPage(pageRequest, filters);
		return area;
	}
	
	/**
	 * 更新仓库区域
	 * @param wareHouseArea
	 * @return
	 */
	@RequestMapping("updateWareHouseArea")
	public @ResponseBody Msg updateWareHouseArea(@Valid WareHouseArea wareHouseArea){
		wareHouseArea.setStatus(COMMON_YES);
		wareHouseAreaService.save(wareHouseArea);
		return success(wareHouseArea);
	}
	
	/**
	 * 删除仓库区域
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteWareHouseArea")
	public @ResponseBody Msg deleteWareHouseArea(@RequestParam("ids")List<Long> ids){
		wareHouseAreaService.deleteAll(ids);
		return success(true);
	}
	
	/**
	 * 查询仓库
	 * @param request
	 * @return
	 */
	@RequestMapping("listWareHouse")
	public @ResponseBody Page<WareHouse> listWareHouse(@RequestParam Long parentid){
		List<WareHouse> warehouse =  wareHouseAreaService.findWareHouse(parentid);
		Page<WareHouse> page = new Page<WareHouse>();
		page.setResult(warehouse);
		return page;
	}	
	
	/**
	 * 更新仓库
	 * @param platform
	 * @return
	 */
	@RequestMapping("updateWareHouse")
	public @ResponseBody Msg updateWareHouse(@Valid WareHouse wareHouse){
		WareHouseArea area = new WareHouseArea();
		area.setId(wareHouse.getParentid());
		wareHouse.setWharea(area);
		wareHouseAreaService.updateWareHouse(wareHouse);
		return success(wareHouse);
	}	
	
	/**
	 * 删除仓库
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteWareHouse")
	public @ResponseBody Msg deleteWareHouse(@RequestParam("ids")List<Long> ids){
		wareHouseAreaService.deleteWareHouse(ids);
		return success(true);
	}	
	
	/**
	 * 获取所有仓库区域
	 * @return
	 */
	@RequestMapping("getAllArea")
	public @ResponseBody Page<WareHouseArea> getAllArea(){
		Page<WareHouseArea> area = new Page<WareHouseArea>();
		area.setResult(wareHouseAreaService.getAllArea());
		return area;
	}
	
	/**
	 * 获取所有仓库
	 * @return
	 */
	@RequestMapping("getAllWareHouse")
	public @ResponseBody Page<WareHouse> getAllWareHouse(){
		Page<WareHouse> warehouse = new Page<WareHouse>();
		warehouse.setResult(wareHouseAreaService.getAllWareHouse());
		return warehouse;
	}
	
}
