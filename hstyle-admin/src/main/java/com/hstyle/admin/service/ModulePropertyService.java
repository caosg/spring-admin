package com.hstyle.admin.service;

import java.util.Date;
import java.util.List;

import com.hstyle.admin.model.ModuleProperty;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.exception.ApplicationException;
import com.hstyle.framework.service.GenericService;
/**
 * 
 * @author Administrator
 *
 */
public interface ModulePropertyService extends GenericService<ModuleProperty,Long>{
	/**
	 * 通过模块代码获取模块配置
	 * @param code  模块代码
	 * @return
	 */
	List<ModuleProperty> getPropertyByModule(String modcode);
	
	/**
	 * 通过模块属性代码和模块代码获取模块配置
	 * @param code    模块配置代码 
	 * @param modCode 模块代码
	 * @return
	 */
	List<ModuleProperty> getPropertyByCode(String code,String modCode);
	/**
	 * 通过模块代码和模块配置名称获取去模块配置
	 * @param name    模块配置名称
	 * @param modCode 模块配置代码
	 * @return
	 */
	List<ModuleProperty> getPropertyByName(String name,String modCode);


	/**
	 * 模块配置管理修改
	 * @param properties
	 * @param moduleType 
	 * @param busicode 
	 */
	 void updateConfig(String properties, String busicode, String moduleType) throws ApplicationException;

	/**
	 * 通过序号和模块代码获取模块配置
	 * @param orderNo  序号
	 * @param modCode  模块代码
	 * @return
	 */
	List<ModuleProperty> getPropertyByOrderNo(String orderNo,String modCode) ;
	/**
	 * 
	 * @param code
	 * @param modCode
	 * @return
	 */
	long getLongValueByCode(String code,String modCode) throws Exception;
	/**
	 * 
	 * @param code
	 * @param modCode
	 * @return
	 */
	String getStringValueByCode(String code,String modCode)throws Exception;
	/**
	 * 
	 * @param code
	 * @param modCode
	 * @return
	 */
	List<String> getListValueByCode(String code,String modCode) throws Exception;
	/**
	 * 
	 * @param code
	 * @param modCode
	 * @return
	 * @throws ApplicationException
	 */
	Date getDateValueByCode(String code,String modCode) throws Exception;

	/**
	 * 配置项定义--修改配置项
	 * @param property
	 * @param mp
	 */
	
	void editModuleProperty(ModuleProperty property, ModuleProperty mp);

	/**
	 * 配置项定义，删除配置项
	 * @param mp
	 */
	void deleteModuleProperty(ModuleProperty mp);

	/**
	 * 系统配置--店铺配置查询
	 * @param module
	 * @param busicode
	 * @return
	 */
	List<ModuleProperty> listSysConfig(String module,String busicode);

	String getStringPropertyByModel(String busicode, String propertycode);
	
	
}
