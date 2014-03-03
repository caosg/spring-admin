/**
 * 
 */
package com.hstyle.shop.dao.hb;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
import com.hstyle.shop.dao.PlatformShopDao;
import com.hstyle.shop.model.PlatformShop;

/**
 * @author Administrator
 *
 */
@Repository
public class PlatformShopDaoHb extends AdvancedHibernateDao<PlatformShop, Long> implements
		PlatformShopDao {
	/* 
	 * 重载父类，支持关联对象别名查询
	 * @see com.hstyle.framework.dao.core.hibernate.BasicHibernateDao#createCriteria(java.lang.Class, java.lang.String, org.hibernate.criterion.Criterion[])
	 */
	@Override
	protected Criteria createCriteria(Class<?> persistentClass,String orderBy,Criterion... criterions) {
        Criteria criteria = super.createCriteria(persistentClass, orderBy, criterions);
        criteria.createAlias("platform", "platform");
        return criteria;
    }
}
