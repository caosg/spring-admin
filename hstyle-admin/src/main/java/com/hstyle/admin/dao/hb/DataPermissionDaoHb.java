package com.hstyle.admin.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.admin.dao.DataPermissionDao;
import com.hstyle.admin.model.DataPermission;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
@Repository
public class DataPermissionDaoHb extends AdvancedHibernateDao<DataPermission, Long> implements
		DataPermissionDao {

}
