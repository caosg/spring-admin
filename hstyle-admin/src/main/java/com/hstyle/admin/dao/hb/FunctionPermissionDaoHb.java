/**
 * 
 */
package com.hstyle.admin.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.admin.dao.FunctionPermissionDao;
import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;

/**
 * @author Administrator
 *
 */
@Repository
public class FunctionPermissionDaoHb extends AdvancedHibernateDao<FunctionPermission, Long> implements
		FunctionPermissionDao {

	@Override
	public Long getChildrenSize(Long menuId) {
		String sql="from FunctionPermission X where X.parent.id =?";
		return super.countHqlResult(sql, menuId);
	}

}
