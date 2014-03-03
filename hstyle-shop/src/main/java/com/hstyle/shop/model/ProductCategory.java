/**
 * 
 */
package com.hstyle.shop.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Lists;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;

/**
 * 商品类目
 * @author jiaoyuqiang
 *
 */
@Entity
@Table(name = "sys_product_category")
@StateDelete(propertyName = "status",type =PropertyType.S,value="0")
//默认的缓存策略.
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductCategory extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8781560686656323267L;
	public static final long ROOT_ID=-1;
	private String code;//类目编码
	private String name;//类目名称
	private Integer level;//类目层级
	private Integer orderNum;//顺序(允许空)	
	private Long parentId;//上级类目id
	private List<ProductCategory> children=Lists.newArrayList();//下属类目集合
	private String text;//前端ext显示名称
	private String status;
	private Boolean leaf;
	private Boolean checked=false;
	@Transient
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}	
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
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
	@Transient
	public List<ProductCategory> getChildren() {
		return children;
	}
	public void setChildren(List<ProductCategory> children) {
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
	
	
	
	
}
