/**
 * 
 */
package com.hstyle.admin.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Lists;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;

/**
 * 组织机构
 * @author Administrator
 *
 */
@Entity
@Table(name = "sys_dept")
@StateDelete(propertyName = "status",type =PropertyType.S,value="2")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dept extends IdEntity {

	private static final long serialVersionUID = -5762920761170978529L;
	public static final long ROOT_ID=-1;
	private String code;//部门编码,10/1010/101010,分别对应一级、二级、三级
	private String name;//部门名称
	private Integer level;//部门级别
	private Integer orderNum;//顺序	
	private Long parentId;//上级部门id
	private String status;//状态
	private String remark;
	private List<Dept> children=Lists.newArrayList();//下属部门集合
	private String text;//前端ext显示名称
	@Column(length=64,nullable=false,unique=true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=64,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false)
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	public Dept getParent() {
		return parent;
	}
	public void setParent(Dept parent) {
		this.parent = parent;
	}*/
	@Transient
	public List<Dept> getChildren() {
		return children;
	}
	public void setChildren(List<Dept> children) {
		this.children = children;
	}
	/**
	 * @return the parentId
	 */
	@Column(name="parent_id")
	public Long getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the orderNum
	 */
	@Column(name="order_num")
	public Integer getOrderNum() {
		return orderNum;
	}
	/**
	 * @param orderNum the orderNum to set
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	/**
	 * @return the text
	 */
	@Transient
	public String getText() {
		text=name;
		return text;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
}
