/**
 * 
 */
package com.hstyle.admin.model;

import javax.persistence.Column;
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
 * 
 * 数据字典映射表
 * 1:数据字典子项信息和其他平台的映射表
 * @author jiaoyuqiang
 *
 */
@Entity
@Table(name="sys_dictionary_mapping")
@StateDelete(propertyName = "status",type =PropertyType.S,value="0")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DictionaryMapping extends IdEntity {
	private static final long serialVersionUID = 5988892870622804504L;
	//名称
	private String name;
	//值
	private String value;
	//外键临时保存属性 不做持久化
	private Long dictId;
	//逻辑删除表示 1:正常 0:删除
	private String status;
	//字典子项
	@JsonBackReference
	private Dictionary dictionary;
	//平台
	private String platform;
	
	/**
	 * 
	 * @return
	 */
	@Column(length=64,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return
	 */
	@Column(length=32,nullable=false)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name="dict_id", nullable=false)
	public Dictionary getDictionary() {
		return dictionary;
	}
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
	/**
	 * 
	 * @return
	 */
	@Transient
	public Long getDictId() {
		return dictId;
	}
	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}
	/**
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * @return
	 */
	@Column(length=32,nullable=false)
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
}
