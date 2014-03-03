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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name="t_business_wh")
@StateDelete(propertyName = "status",type =PropertyType.S,value="0")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WareHouse extends IdEntity {

	private static final long serialVersionUID = -8350743248407379670L;
	//仓库代码
	private String whcode;
	//仓库名
	private String whname;
	// 状态 1正常 0删除
	private String status;
	private String whtype;//仓库类型 1：实体仓  2：独享仓 3：虚拟仓
	//外部code
	private String outercode;
	//平台id传值用,不做持久化
	private Long parentid;
	@JsonBackReference
	private WareHouseArea wharea;
	//页面使用取值(多用于bi分析动态生成groupcheckbox页面),不做持久化
	private Long areaid;
	
	@Transient
	public Long getAreaid() {
		return wharea.getId();
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	@Transient
	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public String getWhcode() {
		return whcode;
	}

	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}

	public String getWhname() {
		return whname;
	}

	public void setWhname(String whname) {
		this.whname = whname;
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
	@JoinColumn(name="whareaid", nullable=false)
	public WareHouseArea getWharea() {
		return wharea;
	}

	public void setWharea(WareHouseArea wharea) {
		this.wharea = wharea;
	}

	public String getWhtype() {
		return whtype;
	}

	public void setWhtype(String whtype) {
		this.whtype = whtype;
	}
	
	
}
