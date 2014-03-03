package com.hstyle.admin.service;

/**
 *  配置服务
 * @author Administrator
 *
 */
public interface AppConfig {
	/**
	 * 默认返回系统配置属性值
	 * 
	 * @param propetyCode  model.propertycode 如：product.xxx
	 *          配置项code  busicode
	 * @return
	 */
	String getStringProperty(String propetyCode);
	
	boolean getBooleanProperty(String propetyCode);

	/**
	 * 根据模块code返回配置项属性值
	 * @param modelCode  业务模块busicode
	 * @param propertyCode  大模块.配置项code
	 * @return
	 */

	String getStringPropertyByModel(String modelCode, String propertyCode);
	
	boolean getBooleanPropertyByModel(String modelCode, String propertyCode);
}
