/**
 * 
 */
package com.hstyle.shop.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.service.DefaultServiceImp;
import com.hstyle.shop.dao.WareHouseAreaDao;
import com.hstyle.shop.dao.WareHouseDao;
import com.hstyle.shop.model.WareHouse;
import com.hstyle.shop.model.WareHouseArea;
import com.hstyle.shop.service.WareHouseAreaService;

/**
 * 仓库
 * @author jiaoyuqiang
 *
 */
@Service("wareHouseAreaService")
public class WareHouseAreaServiceImp extends DefaultServiceImp<WareHouseArea, Long> implements
	WareHouseAreaService {

	private WareHouseAreaDao wareHouseAreaDao;
	
	@Autowired
	private WareHouseDao wareHouseDao;
	
	@Autowired
	public void setWareHouseAreaDao(WareHouseAreaDao wareHouseAreaDao) {
		this.wareHouseAreaDao = wareHouseAreaDao;
		dao=wareHouseAreaDao;
	}

	@Override
	public Page<WareHouse> findWareHousePage(PageRequest pageRequest,
			List<PropertyFilter> filters) {
		// TODO Auto-generated method stub
		return wareHouseDao.findPage(pageRequest, filters);
	}
	
	@Override
	public void updateWareHouse(WareHouse wareHouse) {
		wareHouse.setStatus("1");//正常状态
		wareHouseDao.save(wareHouse);
		
	}
	
	@Override
	public void deleteWareHouse(List<Long> id) {
		wareHouseDao.deleteAll(id);
		
	}

	@Override
	public List<WareHouse> findWareHouse(Long parentid) {
		return wareHouseDao.findByExpressions(new String[]{"EQ_L_wharea.id","EQ_S_status"}, new String[]{parentid+"","1"});
	}

	@Override
	public List<WareHouseArea> getAllArea() {
		return wareHouseAreaDao.findByExpressions(new String[]{"EQ_S_status"}, new String[]{"1"});
	}

	@Override
	public List<WareHouse> getAllWareHouse() {
		return wareHouseDao.findByExpressions(new String[]{"EQ_S_status"}, new String[]{"1"});
	}
	
}
