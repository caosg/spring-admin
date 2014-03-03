/**
 * 
 */
package com.hstyle.shop.service;

import java.util.List;

import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.service.GenericService;
import com.hstyle.shop.model.WareHouse;
import com.hstyle.shop.model.WareHouseArea;

/**
 * @author Administrator
 *
 */
public interface WareHouseAreaService extends GenericService<WareHouseArea, Long> {
	

	public Page<WareHouse> findWareHousePage(PageRequest pageRequest , List<PropertyFilter> filters);
	
	
	public void updateWareHouse(WareHouse wareHouse);
	
	
	public void deleteWareHouse(List<Long> id);
	
	
	public List<WareHouse> findWareHouse(Long parentid);
	
	
	public List<WareHouseArea> getAllArea();
	
	
	public List<WareHouse> getAllWareHouse();
	
}
