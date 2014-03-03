/**
 * 
 */
package com.hstyle.shop.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
import com.hstyle.shop.dao.ProductCategoryDao;
import com.hstyle.shop.model.ProductCategory;

/**
 * 
 * 商品类目管理hibernate方式实现dao
 * @author jiaoyuqiang
 *
 */
@Repository
public class ProductCategoryDaoHb extends AdvancedHibernateDao<ProductCategory, Long> implements ProductCategoryDao {

	@Override
	public long getChlidNum(Long parentId) {
		String hql="from ProductCategory t where t.parentId=?";
		return super.countHqlResult(hql, parentId);
	}

}
