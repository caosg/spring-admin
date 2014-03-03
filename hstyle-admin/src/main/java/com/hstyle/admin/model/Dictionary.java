/**
 * 
 */
package com.hstyle.admin.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.collect.Lists;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.model.IdEntity;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name="sys_dictionary")
@StateDelete(propertyName = "isdel",type =PropertyType.S,value="0")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedQueries({
	@NamedQuery(name=Dictionary.FindByTypeCode,query="from Dictionary dd where dd.dictionaryType.code = ?"),
	@NamedQuery(name=Dictionary.GetNameByKey,query="from Dictionary dd where dd.dictionaryType.code = ? and dd.value= ? ")
})
public class Dictionary extends IdEntity {

	private static final long serialVersionUID = 7027538945159642216L;
	
	public static final String FindByTypeCode="FIND_BY_TYPE_CODE";
	
	public static final String GetNameByKey="GET_NAME_BY_KEY";
	
	//名称
	private String name;
	//值
	private String value;
	//备注
	private String remark;
	//外键临时保存属性 不做持久化
	private Long parentid;
	//逻辑删除表示 1:正常 0:删除
	private String isdel;
	//字典类别
	@JsonBackReference
	private DictionaryType dictionaryType;
	//映射表集合
	private List<DictionaryMapping> dictMappings = Lists.newArrayList();
	
	/**
	 * 
	 * @return
	 */
	@OneToMany(fetch=FetchType.LAZY,mappedBy="dictionary")
	@Fetch(FetchMode.SUBSELECT)
	public List<DictionaryMapping> getDictMappings() {
		return dictMappings;
	}
	public void setDictMappings(List<DictionaryMapping> dictMappings) {
		this.dictMappings = dictMappings;
	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 
	 * @return
	 */
	@ManyToOne
	@JoinColumn(name="type_id", nullable=false)
	public DictionaryType getDictionaryType() {
		return dictionaryType;
	}
	public void setDictionaryType(DictionaryType dictionaryType) {
		this.dictionaryType = dictionaryType;
	}
	@Transient
	public Long getParentid() {
		return parentid;
	}
	
	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}	
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}	
}
