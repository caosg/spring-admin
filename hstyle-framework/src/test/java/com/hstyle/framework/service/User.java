package com.hstyle.framework.service;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 使用JAXB2.0标注的待转换Java Bean.
 */
//根节点
@XmlRootElement
//指定子节点的顺序
@XmlType(propOrder = { "name", "roles" })
public class User {

	private Long id;
	private String name;
	private String password;

	private List<Role> roles = Lists.newArrayList();


	//设置转换为xml节点中的属性
	@XmlAttribute
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//设置不转换为xml
	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//设置对List<Object>的映射, xml为<roles><role id="1" name="admin"/></roles>
	@XmlElementWrapper
	@XmlElement(name = "role")
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
