package com.hstyle.shop.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hstyle.framework.model.IdEntity;
@Entity
@Table(name="t_business_proinfo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BusinessProinfo extends IdEntity {
	private static final long serialVersionUID = 731875422274252539L;
	private String procode;//货号
	private String createdate;//创建时间
	private String status;//状态
	private String onsaledate;//上架时间
	private String stopmonitor;//停止监控时间
	private String downdate;//下架时间
	private Date created;//创建时间
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOnsaledate() {
		return onsaledate;
	}
	public void setOnsaledate(String onsaledate) {
		this.onsaledate = onsaledate;
	}
	public String getStopmonitor() {
		return stopmonitor;
	}
	public void setStopmonitor(String stopmonitor) {
		this.stopmonitor = stopmonitor;
	}
	public String getDowndate() {
		return downdate;
	}
	public void setDowndate(String downdate) {
		this.downdate = downdate;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
}
