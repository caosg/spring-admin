/**
 * 
 */
package com.hstyle.shop.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
import com.hstyle.shop.dao.PlatformDao;
import com.hstyle.shop.model.Platform;

/**
 * @author Administrator
 *
 */
@Repository
public class PlatformDaoHb extends AdvancedHibernateDao<Platform, Long> implements
		PlatformDao {

}
