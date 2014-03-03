/**
 * 
 */
package com.hstyle.shop.dao;

import com.hstyle.framework.dao.core.AdvancedDao;
import com.hstyle.shop.model.ProductCategory;

/**
 * 商品类目管理dao
 * @author jiaoyuqiang
 *
 */
public interface ProductCategoryDao extends AdvancedDao<ProductCategory, Long> {
	/**
	 * 获得直属类目数量,包括已删除的
	 * 
	 * @param parentId
	 * @return
	 */
	long getChlidNum(Long parentId);
}
