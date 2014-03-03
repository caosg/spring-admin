/**
 * 
 */
package com.hstyle.shop.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.excel.imp.util.StringUtil;
import com.hstyle.framework.service.DefaultServiceImp;
import com.hstyle.shop.dao.PlatformDao;
import com.hstyle.shop.dao.PlatformShopDao;
import com.hstyle.shop.dao.ShopAppDao;
import com.hstyle.shop.model.Platform;
import com.hstyle.shop.model.PlatformShop;
import com.hstyle.shop.model.ShopApp;
import com.hstyle.shop.service.PlatformService;

/**
 * 平台/店铺
 * @author jiaoyuqiang
 *
 */
@Service("platformService")
public class PlatformServiceImp extends DefaultServiceImp<Platform, Long> implements
		PlatformService {

	private PlatformDao platformDao;
	
	@Autowired
	public void setPlatformDao(PlatformDao platformDao) {
		this.platformDao = platformDao;
		dao=platformDao;
	}
	@Autowired
	private PlatformShopDao platformShopDao;
	@Autowired
	private ShopAppDao shopAppDao;
	
	
	
	@Override
	@Cacheable(value="platshopCache")
	public Page<PlatformShop> findShopPage(PageRequest pageRequest,
			List<PropertyFilter> filters) {
		return platformShopDao.findPage(pageRequest, filters);
	}
	
	@Override
	@CacheEvict(value="platshopCache",allEntries=true)
	public void updateShop(PlatformShop platformShop) {
		platformShop.setStatus("1");//正常状态
		platformShopDao.save(platformShop);
		
	}
	
	@Override
	@CacheEvict(value="platshopCache",allEntries=true)
	public void deleteShop(List<Long> ids) {
		platformShopDao.deleteAll(ids);
		
	}

	@Override
	public Page<Platform> findPlatformPage(PageRequest pageRequest,
			Platform platform) {
		return platformDao.findByEntityPaged(platform, pageRequest);
	}

	@Override
	@Cacheable(value="platshopCache")
	public List<PlatformShop> findShop(Long parentid) {
		return platformShopDao.findByExpressions(new String[]{"EQ_L_platform.id","EQ_S_status"}, new String[]{parentid+"","1"});
	}

	@Override
	public List<Platform> getAllPlatform() {
		return platformDao.findByExpressions(new String[]{"EQ_S_status"}, new String[]{"1"});
	}

	@Override
	@Cacheable(value="platshopCache")
	public List<PlatformShop> getAllShop() {
		return platformShopDao.findByExpressions(new String[]{"EQ_S_status"}, new String[]{"1"});
	}

	@Override
	public List<ShopApp> findShopApp(Long shopid) {
		return shopAppDao.findByExpressions(new String[]{"EQ_L_platformShop.id","EQ_S_status"}, new String[]{shopid+"","1"});
	}

	@Override
	public void updateShopApp(ShopApp shopapp) {
		shopapp.setStatus("1");
		shopAppDao.save(shopapp);
	}

	@Override
	public void deleteShopApp(List<Long> ids) {
		shopAppDao.deleteAll(ids);
	}

	@Override
	@Cacheable(value="platshopCache")
	public List<PlatformShop> findByShopCode(String shopcode) {
		return platformShopDao.findByProperty("shopcode", shopcode);
	}

	@Override
	@Cacheable(value="platshopCache")
	public Platform getPltByShopcode(String shopCode) {
		log.info("根据店铺代码获取平台----传入店铺代码为："+shopCode);
		if(StringUtil.isEmpty(shopCode))
			return null;
		return platformShopDao.findUniqueByExpression("EQ_S_shopcode", shopCode).getPlatform();
	}

}
