/**
 * 
 */
package com.hstyle.shop.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="t_business_shopapp")
@StateDelete(propertyName = "status",type =PropertyType.S,value="0")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShopApp extends IdEntity {

	private static final long serialVersionUID = 7911821356651252077L;

    private Long shopid;//shop表主键
    
    private String appkey;
    
    private String appsecrect;
    
    private String appsession;
    
    private String tokensession;
    
    private String status;//状态 0:删除 1:正常
    
    private PlatformShop platformShop;
    
	@ManyToOne
	@JoinColumn(name="shopid", nullable=false)
	@JsonIgnore
	public PlatformShop getPlatformShop() {
		return platformShop;
	}

	public void setPlatformShop(PlatformShop platformShop) {
		this.platformShop = platformShop;
	}
	
	@Transient
	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppsecrect() {
		return appsecrect;
	}

	public void setAppsecrect(String appsecrect) {
		this.appsecrect = appsecrect;
	}

	public String getAppsession() {
		return appsession;
	}

	public void setAppsession(String appsession) {
		this.appsession = appsession;
	}

	public String getTokensession() {
		return tokensession;
	}

	public void setTokensession(String tokensession) {
		this.tokensession = tokensession;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
