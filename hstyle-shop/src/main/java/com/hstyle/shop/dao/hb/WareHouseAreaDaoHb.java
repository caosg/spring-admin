/**
 * 
 */
package com.hstyle.shop.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
import com.hstyle.shop.dao.WareHouseAreaDao;
import com.hstyle.shop.model.WareHouseArea;

/**
 * @author Administrator
 *
 */
@Repository
public class WareHouseAreaDaoHb extends AdvancedHibernateDao<WareHouseArea, Long> implements
		WareHouseAreaDao {

}
