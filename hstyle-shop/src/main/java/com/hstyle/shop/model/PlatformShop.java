/**
 * 
 */
package com.hstyle.shop.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="t_business_pltshop")
@StateDelete(propertyName = "status",type =PropertyType.S,value="0")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PlatformShop extends IdEntity {

	private static final long serialVersionUID = 5348229834725912635L;
	//店铺代码
	private String shopcode;
	//店铺名称
	private String shopname;
	//状态 0删除 1正常
	private String status;
    //外部代码
	private String outercode;
	//字典类别
	@JsonIgnore
	private Platform platform;
	//平台id 不做持久化 传值用
	private Long parentid;
	//页面使用取值(多用于bi分析动态生成groupcheckbox页面),不做持久化
	private Long platformid;
	
	private String tbtype;//区分商城店：0 和 集市店：1
	
	//app对象
	private Set<ShopApp> shopapps = new HashSet<ShopApp>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="platformShop")
	@Fetch(FetchMode.SUBSELECT)
	@JsonIgnore
	public Set<ShopApp> getShopapps() {
		return shopapps;
	}
	public void setShopapps(Set<ShopApp> shopapps) {
		this.shopapps = shopapps;
	}
	@Transient
	public Long getPlatformid() {
		return platform.getId();
	}
	public void setPlatformid(Long platformid) {
		this.platformid = platformid;
	}
	@Transient
	public Long getParentid() {
		return parentid;
	}
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}
	public String getShopcode() {
		return shopcode;
	}
	public void setShopcode(String shopcode) {
		this.shopcode = shopcode;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
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
	@ManyToOne
	@JoinColumn(name="pltfomid", nullable=false)
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	public String getTbtype() {
		return tbtype;
	}
	public void setTbtype(String tbtype) {
		this.tbtype = tbtype;
	}
	
}
