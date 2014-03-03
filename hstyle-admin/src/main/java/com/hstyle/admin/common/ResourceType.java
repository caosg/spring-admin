/**
 * 
 */
package com.hstyle.admin.common;


/**
 * 资源类型
 * @author Administrator
 *
 */
public enum ResourceType {
	/**
	 * 菜单类型，该类型为用户可以见的
	 */
	Menu("1","菜单"),
	/**
	 * 功能类型
	 */
	Function("2","功能"),
	
	/**操作
	 * 
	 */
	Action("3","操作");
	
	ResourceType(String value,String name) {
		this.name = name;
		this.value = value;
	}
	
	private String name;
	
	private String value;
	
	/**
	 * 获取资源类型名称
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取资源类型值
	 * @return
	 */
	public String getValue() {
		return value;
	}
}
