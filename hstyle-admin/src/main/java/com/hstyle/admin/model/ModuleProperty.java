package com.hstyle.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hstyle.framework.model.IdEntity;

@Entity
@Table(name="sys_options")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
/**
 * 模块配置
 */
public class ModuleProperty extends IdEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2695496377714570280L;
	private String code;//代码
	private String name;//配置名称
	private String module;//所属模块
	private String type;//类型
	private String options;//可选项
	private String defaultValue;//默认值
	private long orderNo;//排序号
	private String remark;//描述
	private String value;//值,
	private String moduleType;//父级类型 系统级还是店铺级
	private String busicode;//业务code 系统的是SYS，店铺的是店铺code,初始是GLOBAL
	@Column(nullable=false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false)
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	@Column(nullable=false)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	@Column(name="order_no")
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getModuleType() {
		return moduleType;
	}
	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}
	public String getBusicode() {
		return busicode;
	}
	public void setBusicode(String busicode) {
		this.busicode = busicode;
	}
	
	
}
