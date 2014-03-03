/**
 * 
 */
package com.hstyle.framework.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 字符串型主键
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class UuIdEntity extends Entity{

	protected String id;

	/**
	 * 获取主键ID
	 * 
	 * @return String
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@Column(length = 32, nullable = false, unique = true)
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return this.id;
	}

	/**
	 * 设置主键ID，
	 * 
	 * @param id
	 */
	public void setId(String id) {

			this.id = id;

	}

}
