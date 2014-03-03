package com.hstyle.shop.dao.hb;

import org.springframework.stereotype.Repository;

import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
import com.hstyle.shop.dao.LogisticCompanyDao;
import com.hstyle.shop.model.LogisticCompany;
@Repository("logisticCompanyDao")
public class LogisticCompanyDaoHb extends AdvancedHibernateDao<LogisticCompany,Long> implements LogisticCompanyDao {

	

}
