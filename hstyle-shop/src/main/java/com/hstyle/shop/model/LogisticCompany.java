package com.hstyle.shop.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;
@Entity
@Table(name="t_business_logisticcompany")
@StateDelete(propertyName = "status",type =PropertyType.S,value="0")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LogisticCompany extends IdEntity{
	
	private static final long serialVersionUID = 1L;

	//物流公司代码
	private String companycode;
	//物流公司名称
	private String companyname;
	//物流公司外部代码
	private String outercode;
	//状态1正常 0删除
	private String status;
	
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getOutercode() {
		return outercode;
	}
	public void setOutercode(String outercode) {
		this.outercode = outercode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
