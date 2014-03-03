/**
 * 
 */
package com.hstyle.admin.common;

/**
 * 字典类型枚举
 * @author Administrator
 *
 */
public enum DictionaryCode {
	
	/**
	 * 状态类型
	 */
	State("USER_STATE"),	
	/**
	 * 资源类型
	 */
	ResourceType("RESOURCE_TYPE");
	
	private String code;
	
	private DictionaryCode(String code) {
		this.code = code;
	}
	
	/**
	 * 获取类型代码
	 * 
	 * @return String
	 */
	public String getCode() {
		return this.code;
	}

}
