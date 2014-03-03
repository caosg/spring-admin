/**
 * 
 */
package com.hstyle.admin.common;

/**
 * 状态枚举
 * 
 * @author 
 *
 */
public enum State{
	
	/**
	 * 启用
	 */
	Enable("0","启用"),
	/**
	 * 禁用
	 */
	Disable("1","禁用"),
	/**
	 * 删除
	 */
	Delete("2","删除");
	
	//值
	private String value;
	//名称
	private String name;
	
	private State(String value,String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * 获取值
	 * @return Integer
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获取名称
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
}