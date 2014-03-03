/**
 * 
 */
package com.hstyle.shop.service;

import java.util.List;

import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.service.GenericService;
import com.hstyle.shop.model.Platform;
import com.hstyle.shop.model.PlatformShop;
import com.hstyle.shop.model.ShopApp;

/**
 * @author Administrator
 *
 */
public interface PlatformService extends GenericService<Platform, Long> {
	
	/**
	 * 按条件查询店铺
	 * @param pageRequest
	 * @param filters
	 * @return
	 */
	public Page<PlatformShop> findShopPage(PageRequest pageRequest , List<PropertyFilter> filters);
	
	/**
	 * 更新店铺
	 * @param platformShop
	 */
	public void updateShop(PlatformShop platformShop);
	
	/**
	 * 删除店铺
	 * @param id
	 */
	public void deleteShop(List<Long> ids);
	
	/**
	 * 查询平台
	 * @param pageRequest
	 * @param platform
	 * @return
	 */
	public Page<Platform> findPlatformPage(PageRequest pageRequest , Platform platform);
	
	/**
	 * 根据平台查询店铺
	 * @param parentid
	 * @return
	 */
	public List<PlatformShop> findShop(Long parentid);
	
	/**
	 * 获取所有平台
	 * @return
	 */
	public List<Platform> getAllPlatform();
	
	/**
	 * 获取所有店铺
	 * @return
	 */
	public List<PlatformShop> getAllShop();
	
	/**
	 * 根据店铺查询app
	 * @param shopid
	 * @return
	 */
	public List<ShopApp> findShopApp(Long shopid);
	
	/**
	 * 更新店铺app
	 * @param shopapp
	 */
	public void updateShopApp(ShopApp shopapp);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteShopApp(List<Long> ids);
	
	
	public List<PlatformShop> findByShopCode(String shopcode);
	/**
	 * @author liuning
	 * @Description: 根据店铺code获得对应的平台
	 * @date 2014-1-6下午4:32:25
	 * @param @param shopCode 店铺code
	 * @return Platform 平台
	 */
	public Platform getPltByShopcode(String shopCode);
}
