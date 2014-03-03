package com.hstyle.framework.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBean;

import com.google.common.collect.Lists;
import com.hstyle.framework.dao.core.AdvancedDao;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
import com.hstyle.framework.model.User;

@SpringApplicationContext( {"applicationContext.xml" })
@DataSet("/test-data.xml")
public class GenericDaoUnitilsTest extends UnitilsJUnit4 {
	    Logger log = LoggerFactory.getLogger(getClass());
	    AdvancedDao<User, Long> genericDao;
	    @SpringBean("sessionFactory")
	    SessionFactory sessionFactory;

	    @Before
	    public void setUp() {
	        genericDao = new AdvancedHibernateDao<User, Long>(User.class);
	    }

	    @Test
	    public void getUser() {
	        User user = genericDao.get(1L);
	        assertNotNull(user);
	        assertEquals("user", user.getUsername());
	        assertEquals(1, user.getRoles().size());
	        List<User> users=genericDao.findByEntity(user);
	        assertEquals(1, users.size());
	        assertEquals("user", users.get(0).getUsername());
	        log.info("username is {},email is {}--",user.getUsername(),user.getEmail());
	    }
	    @Test
	    public void findAllUser(){
	    	List<User> users =genericDao.getAll();
	    	assertEquals(3, users.size());
	    }
	    @Test
	    public void save(){
	    	//测试保存数据
	    	User user = new User();
	    	user.setUsername("csg");user.setFirstName("曙光");user.setPassword("1234");
	    	user.setLastName("曹");user.setEmail("111@qq.com");
	    	genericDao.save(user);
	    	List<User> users =genericDao.getAll();
	    	assertEquals(4, users.size());
	    	//测试条件查询
	    	User user2=new User();user2.setFirstName("曙光");
	    	List<User> result=genericDao.findByEntity(user2);
	    	assertEquals("曙光", result.get(0).getFirstName());
	    	log.info("user is {}",result.get(0));
	    	
	    }
	    @Test
	    public void update(){
	    	User user = genericDao.get(1L);
	    	assertEquals("Tomcat", user.getFirstName());
	    	user.setFirstName("csg");
	    	genericDao.update(user);
	    	User updateUser = genericDao.get(1L);
	    	assertEquals("csg", updateUser.getFirstName());
	    	
	    }
	    @Test
	    public void delete(){
	    	assertEquals(3, genericDao.getAll().size());
	    	genericDao.delete(1L);
	    	User user = genericDao.get(1L);
	    	assertNull(user);
	    	List<Long> ids=Lists.newArrayList(2L, 3L);
	    	genericDao.deleteAll(ids);
	    	List<User> result=genericDao.getAll();
	    	assertEquals(0,result.size());
	    }
}
