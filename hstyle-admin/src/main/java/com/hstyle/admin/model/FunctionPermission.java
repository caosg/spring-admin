/**
 * 
 */
package com.hstyle.admin.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name="sys_permission_function")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQueries({
	@NamedQuery(name=FunctionPermission.GetMenuChildren,query="from FunctionPermission f where f.parent.id = ? and f.resource='1'")
	
})
public class FunctionPermission extends Permission {

	private static final long serialVersionUID = 3478100948318649198L;
	
	public static final String GetMenuChildren="GET_MENU_CHILDREN";
	private String url;//资源url
	
	private String iconClsName;//图标
	
	private FunctionPermission parent;//父类
	
	private List<FunctionPermission> children=Lists.newArrayList();//子类
	
	@Column(length=128)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name="icon_cls",length=32)
	public String getIconClsName() {
		return iconClsName;
	}
	public void setIconClsName(String icon) {
		this.iconClsName = icon;
	}
	/**
	 * @return the parent
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@JsonIgnore
	public FunctionPermission getParent() {
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(FunctionPermission parent) {
		this.parent = parent;
	}
	/**
	 * @return the children
	 */
	@OneToMany(fetch=FetchType.EAGER,mappedBy="parent")
	@Fetch(FetchMode.SUBSELECT)
	//@JsonIgnore
	public List<FunctionPermission> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<FunctionPermission> children) {
		this.children = children;
	}
	


}
