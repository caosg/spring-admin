/**
 * 
 */
package com.hstyle.admin.model;

import java.util.List;

import javax.persistence.Column;
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
@Table(name="sys_dictionary_type")
@StateDelete(propertyName = "isdel",type =PropertyType.S,value="0")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DictionaryType extends IdEntity {

	private static final long serialVersionUID = 3681627741346941141L;

	// 名称
	private String name;
	// 代码
	private String code;
	// 备注
	private String remark;
	//是否逻辑删除 1正常 0删除
	private String isdel;
	// 包含的数据字典值
	private List<Dictionary> dictionaries = Lists.newArrayList();
	
	@Column(length=64,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(length=32,nullable=false)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
    @OneToMany(fetch=FetchType.LAZY,mappedBy="dictionaryType")
    @Fetch(FetchMode.SUBSELECT)
	public List<Dictionary> getDictionaries() {
		return dictionaries;
	}
	public void setDictionaries(List<Dictionary> dictionaries) {
		this.dictionaries = dictionaries;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
}
