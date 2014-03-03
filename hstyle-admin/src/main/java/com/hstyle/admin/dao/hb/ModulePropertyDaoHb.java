package com.hstyle.admin.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.admin.dao.ModulePropertyDao;
import com.hstyle.admin.model.ModuleProperty;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
/**
 * 
 * @author Administrator
 *
 */
@Repository
public class ModulePropertyDaoHb extends AdvancedHibernateDao<ModuleProperty, Long> implements ModulePropertyDao{
	
}
