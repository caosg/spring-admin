/**
 * 
 */
package com.hstyle.admin.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.admin.dao.DictionaryTypeDao;
import com.hstyle.admin.model.DictionaryType;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;

/**
 * @author Administrator
 *
 */
@Repository
public class DictionaryTypeDaoHb extends AdvancedHibernateDao<DictionaryType, Long> implements
		DictionaryTypeDao {

}
