package com.hstyle.framework.service;

import com.hstyle.framework.mapper.JaxbMapper;



public class XmlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User user = new User();
		user.setId(1L);
		user.setName("calvin");

		user.getRoles().add(new Role(1L, "admin"));
		user.getRoles().add(new Role(2L, "user"));
		JaxbMapper jaxbMapper=new JaxbMapper(User.class);
		String xml = jaxbMapper.toXml(user, "GBK");
		System.out.println("Jaxb Object to Xml result:\n" + xml);
	}

}
