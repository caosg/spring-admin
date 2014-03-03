/**
 * 
 */
package com.hstyle.shop.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name="t_business_pltfom")
@StateDelete(propertyName = "status",type =PropertyType.S,value="0")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Platform extends IdEntity {

	private static final long serialVersionUID = -8162421100913766413L;
	// 平台代码
	private String pltfomcode;
	// 平台名称
	private String pltfomname;
	// 状态 1正常 0删除
	private String status;
	//外部code
	private String outercode;	
	//
	private Set<PlatformShop> shops = new HashSet<PlatformShop>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="platform")
	@Fetch(FetchMode.SUBSELECT)
	@JsonIgnore
	public Set<PlatformShop> getShops() {
		return shops;
	}
	public void setShops(Set<PlatformShop> shops) {
		this.shops = shops;
	}
	public String getPltfomcode() {
		return pltfomcode;
	}
	public void setPltfomcode(String pltfomcode) {
		this.pltfomcode = pltfomcode;
	}
	public String getPltfomname() {
		return pltfomname;
	}
	public void setPltfomname(String pltfomname) {
		this.pltfomname = pltfomname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOutercode() {
		return outercode;
	}
	public void setOutercode(String outercode) {
		this.outercode = outercode;
	}
}
