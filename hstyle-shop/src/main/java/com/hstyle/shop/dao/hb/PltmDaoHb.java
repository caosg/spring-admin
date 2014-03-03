/**
 * 
 */
package com.hstyle.shop.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
import com.hstyle.framework.model.IdEntity;
import com.hstyle.shop.dao.PltmDao;

/**
 * @author Administrator
 *
 */
@Repository
public class PltmDaoHb extends AdvancedHibernateDao<IdEntity, Long> implements
		PltmDao {

}
