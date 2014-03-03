/**
 * 
 */
package com.hstyle.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hstyle.framework.model.IdEntity;

/**
 * 权限目标
 * @author Administrator
 *
 */
@Entity
@Table(name="sys_permission_target")
public class DataTarget extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6888834695078390595L;
	private String name;//名称
	private String value;//值
	private String type;//类型   1：实例  2：属性值 3：属性名称  4：上下文值  5:固定值
	private String remark;//说明
	private Long dataId;//
	@Column(nullable=false,length=24)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(nullable=false,length=48)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(nullable=false,length=1)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the dataId
	 */
	@Column(name="data_id")
	public Long getDataId() {
		return dataId;
	}
	/**
	 * @param dataId the dataId to set
	 */
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	

}
