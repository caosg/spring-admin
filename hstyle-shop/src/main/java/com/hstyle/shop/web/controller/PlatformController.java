/**
 * 平台/店铺管理spring-mvc前端控制类
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
import com.hstyle.shop.model.Platform;
import com.hstyle.shop.model.PlatformShop;
import com.hstyle.shop.model.ShopApp;
import com.hstyle.shop.service.PlatformService;


@Controller
@RequestMapping("/platform")
public class PlatformController extends BaseController{
	@Autowired
	private PlatformService platformService;
	private static final String COMMON_YES = "1";

	/**
	 * 查询平台
	 * @param request
	 * @return
	 */
	@RequestMapping("listPlatform")
	public @ResponseBody Page<Platform> listPlatform(HttpServletRequest request){
		PageRequest pageRequest=buildPageRequest(request);//分页参数
		List<PropertyFilter> filters=buildFilterProperties(request);
    	PropertyFilter deptFilter=new PropertyFilter("EQ",PropertyType.S,new String[]{"status"},"1");
    	filters.add(deptFilter);
		Page<Platform> platform =  platformService.findPage(pageRequest, filters);
		return platform;
	}
	
	/**
	 * 更新平台
	 * @param platform
	 * @return
	 */
	@RequestMapping("updatePlatform")
	public @ResponseBody Msg updatePlatform(@Valid Platform platform){
		platform.setStatus(COMMON_YES);
		platformService.save(platform);
		return success(platform);
	}
	
	/**
	 * 删除平台
	 * @param id
	 * @return
	 */
	@RequestMapping("deletePlatform")
	public @ResponseBody Msg deletePlatform(@RequestParam("ids")List<Long> ids){
		platformService.deleteAll(ids);
		return success(true);
	}
	
	/**
	 * 查询店铺
	 * @param request
	 * @return
	 */
	@RequestMapping("listShop")
	public @ResponseBody Page<PlatformShop> listShop(@RequestParam Long parentid){
		List<PlatformShop> shop =  platformService.findShop(parentid);
		Page<PlatformShop> page = new Page<PlatformShop>();
		page.setResult(shop);
		return page;
	}	
	
	/**
	 * 更新店铺
	 * @param platform
	 * @return
	 */
	@RequestMapping("updateShop")
	public @ResponseBody Msg updateShop(@Valid PlatformShop shop){
		Platform platform = new Platform();
		platform.setId(shop.getParentid());
		shop.setPlatform(platform);
		platformService.updateShop(shop);
		return success(shop);
	}	
	
	/**
	 * 删除店铺
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteShop")
	public @ResponseBody Msg deleteShop(@RequestParam("ids")List<Long> ids){
		platformService.deleteShop(ids);
		return success(true);
	}	
	
	/**
	 * 获取所有平台
	 * 1:正常状态
	 * @return
	 */
	@RequestMapping("getCommonAllPlatform")
	public @ResponseBody Page<Platform> getCommonAllPlatform(){
		Page<Platform> platform = new Page<Platform>();
		platform.setResult(platformService.getAllPlatform());
		return platform;
	}
	
	/**
	 * 获取所有店铺
	 * 1:正常状态
	 * @return
	 */
	@RequestMapping("getCommonAllShop")
	public @ResponseBody Page<PlatformShop> getCommonAllShop(){
		Page<PlatformShop> shop = new Page<PlatformShop>();
		shop.setResult(platformService.getAllShop());
	    return shop;
	}
	
	/**
	 * 查询店铺App
	 * @param request
	 * @return
	 */
	@RequestMapping("listShopApp")
	public @ResponseBody Page<ShopApp> listShopApp(@RequestParam Long shopid){
		List<ShopApp> shopapp =  platformService.findShopApp(shopid);
		Page<ShopApp> page = new Page<ShopApp>();
		page.setResult(shopapp);
		return page;
	}	
	
	/**
	 * 更新店铺App
	 * @param platform
	 * @return
	 */
	@RequestMapping("updateShopApp")
	public @ResponseBody Msg updateShopApp(@Valid ShopApp shopapp){
		PlatformShop shop = new PlatformShop();
		shop.setId(shopapp.getShopid());
		shopapp.setPlatformShop(shop);
		platformService.updateShopApp(shopapp);
		return success(shopapp);
	}	
	
	/**
	 * 删除店铺App
	 * @param ids
	 * @return
	 */
	@RequestMapping("deleteShopApp")
	public @ResponseBody Msg deleteShopApp(@RequestParam("ids")List<Long> ids){
		platformService.deleteShopApp(ids);
		return success(true);
	}	
	
}
