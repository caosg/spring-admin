package com.hstyle.framework.test.unitils.hibernate;

import static org.junit.Assert.assertNotNull;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;



@HibernateSessionFactory("hibernate.cfg.xml")
public class BaseDaoTest extends UnitilsJUnit4 {
	@HibernateSessionFactory
	public SessionFactory sessionFactory;
	
	@Test
	public void testSessionFactory(){
		assertNotNull(sessionFactory);
	}
}
