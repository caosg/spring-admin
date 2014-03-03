/**
 * 
 */
package com.hstyle.admin.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.hstyle.framework.model.IdEntity;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "sys_role")
// 默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQueries({
	@NamedQuery(name=Role.FindRoleUsers,query="select distinct u from Role r right join r.users u  where r.id =?")
	
})
public class Role extends IdEntity {

	private static final long serialVersionUID = 8748117390025006067L;
	public  static final String FindRoleUsers="FindRoleUsers";
	
	private String code;//角色代码

	private String name;// 名称
	
	private boolean isAdmin;

	private String remark;// 备注

	private Set<User> users =new HashSet<User>();

	private Set<FunctionPermission> functPermissions = new HashSet<FunctionPermission>();// 拥有的功能权限集合

	private Set<DataPermission> dataPermissions = new HashSet<DataPermission>();// 拥有的数据权限集合

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 多对多定义
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the functPermissions
	 */
	// 多对多定义
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "sys_role_funpermission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "permission_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	@Cascade(value = {CascadeType.DELETE})  
	@JsonIgnore
	public Set<FunctionPermission> getFunctPermissions() {
		return functPermissions;
	}

	/**
	 * @param functPermissions
	 *            the functPermissions to set
	 */
	public void setFunctPermissions(Set<FunctionPermission> functPermissions) {
		this.functPermissions = functPermissions;
	}

	/**
	 * @return the dataPermissions
	 */
	// 多对多定义
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "sys_role_datapermission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "permission_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	@Cascade(value = {CascadeType.DELETE})
	@JsonIgnore
	public Set<DataPermission> getDataPermissions() {
		return dataPermissions;
	}

	/**
	 * @param dataPermissions
	 *            the dataPermissions to set
	 */
	public void setDataPermissions(Set<DataPermission> dataPermissions) {
		this.dataPermissions = dataPermissions;
	}

	/**
	 * @return the code
	 */
	@Column(length=32,unique=true)
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the isAdmin
	 */
	@Column(name="is_admin")
	public boolean isAdmin() {
		return isAdmin;
	}
	@Transient
    public boolean getIsAdmin(){
    	return isAdmin;
    }
	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
