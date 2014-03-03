/**
 * 
 */
package com.hstyle.admin.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.admin.dao.RoleDao;
import com.hstyle.admin.model.Role;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;

/**
 * @author Administrator
 *
 */
@Repository
public class RoleDaoHb extends AdvancedHibernateDao<Role, Long> implements RoleDao {

	

}
