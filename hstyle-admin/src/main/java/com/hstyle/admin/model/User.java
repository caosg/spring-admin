/**
 * 
 */
package com.hstyle.admin.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hstyle.admin.common.State;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_user")
@StateDelete(propertyName = "status",type =PropertyType.S,value="2")
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends IdEntity {
	private static final long serialVersionUID = 8868340171150724707L;
	
	public static final String GetFunction = "GET_FUNCTION";

	private String loginName;//账号	
	private String password;//密码，MD5加密一次保存
	private String plainPassword;//确认密码，不持久化，页面使用
	private String userName;//姓名
	private String position;//职位
	private String email;
	private String status;//状态
	private Dept dept;//所属部门
	private Long deptId;//方便界面展现，前端数据传递
	private String deptName;//方便界面展现
	private boolean isAdmin;//是否是超级管理员
	private String loginMac;//登录mac地址
	private Set<Role> roles=new HashSet<Role>();//拥有的角色集合
	private Set<Dept> depts=new HashSet<Dept>();//兼职部门
	
	@Column(name="login_name",length=32,unique=true,nullable=false)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	@JsonIgnore
	@Column(nullable=false,length=64)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Transient
	public String getPlainPassword() {
		return plainPassword;
	}
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
	@Column(name="user_name",length=16,nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=32)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	// 多对多定义
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	// 多对多定义
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "sys_dept_user", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "dept_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	
	/**
	 * @return the depts
	 */
	public Set<Dept> getDepts() {
		return depts;
	}
	/**
	 * @param depts the depts to set
	 */
	public void setDepts(Set<Dept> depts) {
		this.depts = depts;
	}
	
	@ManyToOne
	@JoinColumn(name="dept_id", nullable=false)
	//@Transient
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	@Transient
	public boolean isEnabled(){
		return status.equals(State.Enable.getValue());
	}
	/**
	 * @return the deptId
	 */
    @Transient
	public Long getDeptId() {
		return deptId;
	}
	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	/**
	 * @return the deptName
	 */
	@Transient
	public String getDeptName() {
		return deptName;
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	/**
	 * @return the isAdmin
	 */
	@Transient
	public boolean isAdmin() {
		return isAdmin;
	}
	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Column(name="login_mac",length=100)
	public String getLoginMac() {
		return loginMac;
	}
	public void setLoginMac(String loginMac) {
		this.loginMac = loginMac;
	}
	
	

}
