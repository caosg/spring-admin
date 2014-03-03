/**
 * 
 */
package com.hstyle.admin.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;


import com.google.common.collect.Lists;
import com.hstyle.framework.model.IdEntity;

/**
 * 权限
 * @author Administrator
 *
 */

@MappedSuperclass
public class Permission extends IdEntity {

	private static final long serialVersionUID = -5944294985194966664L;
	
	private String code;//权限标示
	private String name;//名称	
	private String resource;//资源类型	
	private Boolean leaf;//叶子标志
	private Integer sort;//顺序值		
	private String expression;//权限表达式
	private String remark;//备注
	private boolean roleChecked;//角色拥有标志，非存储属性
	private boolean expanded;//前端展现用，非存储属性
	@Column(length=32,nullable=false,unique=true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=64,nullable=false,unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(nullable=false)
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	/**
	 * @return the leaf
	 */
	public Boolean getLeaf() {
		return leaf;
	}
	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	/**
	 * @return the roleChecked
	 */
	@Transient
	public boolean getRoleChecked() {
		return roleChecked;
	}
	/**
	 * @param roleChecked the roleChecked to set
	 */
	public void setRoleChecked(boolean roleChecked) {
		this.roleChecked = roleChecked;
	}
	/**
	 * @return the expanded
	 */
	@Transient
	public boolean isExpanded() {
		return expanded;
	}
	/**
	 * @param expanded the expanded to set
	 */
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	

}
