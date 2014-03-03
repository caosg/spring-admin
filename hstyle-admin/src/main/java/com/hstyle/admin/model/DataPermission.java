/**
 * 
 */
package com.hstyle.admin.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="sys_permission_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataPermission extends Permission {
	private static final long serialVersionUID = 4711204280322920436L;
	public static final Long ROOT_ID=-1L;//默认根id	
	private DataPermission parent;//父类
	private List<DataPermission> children=Lists.newArrayList();//子类
	private String type;//数据权限分类 domain:领域      action:操作  
	private String action;//执行操作，多个以,分割
	private String operator;//运算符
	private Set<DataTarget> targets;//可操作目标
    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "data_id")
	public Set<DataTarget> getTargets() {
		return targets;
	}

	public void setTargets(Set<DataTarget> targets) {
		this.targets = targets;
	}

	/**
	 * @return the parent
	 */
	@ManyToOne
	@JoinColumn(name = "parent_id")
	@JsonIgnore
	public DataPermission getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(DataPermission parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	@OneToMany(mappedBy="parent" ,cascade={CascadeType.REMOVE})
	
	public List<DataPermission> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<DataPermission> children) {
		this.children = children;
	}

	/**
	 * @return the type
	 */
	@Column(nullable=false,length=1)
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the action
	 */
	
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the operator
	 */
	@Column(length=48)
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

}
