package com.hstyle.framework.dao;


import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.hstyle.framework.dao.core.AdvancedDao;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
import com.hstyle.framework.model.Role;
import com.hstyle.framework.model.User;
import com.hstyle.framework.test.dao.BaseDaoTestCase;

import static org.junit.Assert.*;

public class GenericDaoTest extends BaseDaoTestCase {
    Logger log = LoggerFactory.getLogger(getClass());
    AdvancedDao<User, Long> genericDao;
    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void setUp() {
        genericDao = new AdvancedHibernateDao<User, Long>(User.class,sessionFactory);
        
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
    	//测试分页查询
    	PageRequest pageRequest = new PageRequest(1,2);
    	Page<User> page=genericDao.findPage(pageRequest);
    	assertEquals(2, page.getPageSize());
    	assertEquals(3, page.getTotalItems());
    	assertEquals(2, page.getTotalPages());
    	assertEquals(3, page.getTotalItems());
    	assertEquals(true,page.hasNextPage());
    }
    @Test
    public void save(){
    	//测试保存数据
    	User user = new User();
    	user.setUsername("csg");user.setFirstName("曙光");user.setPassword("1234");
    	user.setLastName("曹");user.setEmail("111@qq.com");
    	user.setAccountExpired(Boolean.FALSE);
    	user.setAccountLocked(Boolean.TRUE);
    	user.setCredentialsExpired(Boolean.FALSE);
    	genericDao.insert(user);
    	List<User> users =genericDao.getAll();
    	assertEquals(4, users.size());
    	//测试条件查询
    	List<User> result=genericDao.findByExpression("EQ_S_firstName", "曙光");
    	assertEquals("111@qq.com", result.get(0).getEmail());
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
    @Test
    public void find(){
    	List<User> users=genericDao.findByExpression("EQ_S_username", "user|admin");
    	assertEquals(2, users.size());
    	List<User> users2=genericDao.findByExpression("IN_S_username", "user,admin");
    	assertEquals(2, users2.size());
    	
    }
}
