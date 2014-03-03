/**
 * 
 */
package com.hstyle.admin.service;

import java.util.List;

import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.framework.service.GenericService;

/**
 * @author Administrator
 * 
 */
public interface FunctionPermissionService extends
		GenericService<FunctionPermission, Long> {
	/**
	 * 获取子菜单
	 * 
	 * @param parentId
	 * @return
	 */
	List<FunctionPermission> getMenuChildren(Long parentId);
	
	/**
	 * 根据菜单id查询该菜单下的所有功能列表
	 * @param menuId
	 * @return
	 */
	List<FunctionPermission> getFunctions(String menuId);
	
	/**
	 * 删除菜单
	 * @param parentId
	 * @return true or false
	 */
	boolean deleteMenu(Long menuId);
}
