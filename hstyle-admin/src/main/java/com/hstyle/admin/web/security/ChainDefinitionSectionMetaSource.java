/**
 * 
 */
package com.hstyle.admin.web.security;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.service.FunctionPermissionService;

/**
 * 借助spring {@link FactoryBean} 对apache shiro的premission进行动态创建
 * @author Administrator
 *
 */
public class ChainDefinitionSectionMetaSource implements FactoryBean<Ini.Section>{
	
	//shiro默认的链接定义
	private String filterChainDefinitions;
	@Autowired
	private FunctionPermissionService functionPermissionService;

	@Override
	public Section getObject() throws Exception {
		List<FunctionPermission> allFunctionPermissions=functionPermissionService.findAll();
		Ini ini = new Ini();
        //加载默认的url
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        for(FunctionPermission permission:allFunctionPermissions){
        	if(StringUtils.isNotEmpty(permission.getUrl()) && StringUtils.isNotEmpty(permission.getExpression())) {
        		
        		section.put(permission.getUrl(), permission.getExpression());
        	}
        }
		return section;
	}
    
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}


	/**
	 * @param filterChainDefinitions the filterChainDefinitions to set
	 */
	public void setFilterChainDefinitions(String filterChainDefinitions) {
		this.filterChainDefinitions = filterChainDefinitions;
	}

}
