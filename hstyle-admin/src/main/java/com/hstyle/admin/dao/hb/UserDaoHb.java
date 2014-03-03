/**
 * 
 */
package com.hstyle.admin.dao.hb;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Repository;

import com.hstyle.admin.dao.UserDao;
import com.hstyle.admin.model.User;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;

/**
 * @author Administrator
 *
 */
@Repository
public class UserDaoHb extends AdvancedHibernateDao<User, Long> implements UserDao {
	/* 
	 * 重载父类，支持关联对象别名查询
	 * @see com.hstyle.framework.dao.core.hibernate.BasicHibernateDao#createCriteria(java.lang.Class, java.lang.String, org.hibernate.criterion.Criterion[])
	 */
	@Override
	protected Criteria createCriteria(Class<?> persistentClass,String orderBy,Criterion... criterions) {
        Criteria criteria = super.createCriteria(persistentClass, orderBy, criterions);
        criteria.createAlias("dept", "dept");
        return criteria;
    }
}
