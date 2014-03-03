/**
 * 
 */
package com.hstyle.shop.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Lists;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name="t_business_wharea")
@StateDelete(propertyName = "status",type =PropertyType.S,value="0")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WareHouseArea extends IdEntity {
	
	private static final long serialVersionUID = -514897615223355146L;
	// 区域代码
	private String whareacode;
	// 区域名称
	private String whareaname;
	// 状态 1正常 0删除
	private String status;
	//外部code
	private String outercode;	
	
	private List<WareHouse> warehouses = Lists.newArrayList();

	
	public String getOutercode() {
		return outercode;
	}

	public void setOutercode(String outercode) {
		this.outercode = outercode;
	}

	public String getWhareacode() {
		return whareacode;
	}

	public void setWhareacode(String whareacode) {
		this.whareacode = whareacode;
	}

	public String getWhareaname() {
		return whareaname;
	}

	public void setWhareaname(String whareaname) {
		this.whareaname = whareaname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="wharea")
	@Fetch(FetchMode.SUBSELECT)
	public List<WareHouse> getWarehouses() {
		return warehouses;
	}

	public void setWarehouses(List<WareHouse> warehouses) {
		this.warehouses = warehouses;
	}
}
