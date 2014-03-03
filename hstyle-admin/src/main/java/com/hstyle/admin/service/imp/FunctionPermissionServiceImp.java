/**
 * 
 */
package com.hstyle.admin.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstyle.admin.common.ResourceType;
import com.hstyle.admin.dao.FunctionPermissionDao;
import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.service.FunctionPermissionService;
import com.hstyle.framework.service.DefaultServiceImp;

/**
 * @author Administrator
 *
 */
@Service
public class FunctionPermissionServiceImp extends DefaultServiceImp<FunctionPermission, Long> implements
		FunctionPermissionService {
	private FunctionPermissionDao functionPermissionDao;
    @Autowired
	public void setPermissionDao(FunctionPermissionDao permissionDao) {
		this.functionPermissionDao = permissionDao;
		this.dao=permissionDao;
	}
	@Override
	public List<FunctionPermission> getMenuChildren(Long parentId) {
		
		return functionPermissionDao.findByQueryNamed(FunctionPermission.GetMenuChildren, parentId);
	}
	@Override
	public List<FunctionPermission> getFunctions(String menuId) {
		return functionPermissionDao.findByExpressions(new String[]{"EQ_L_parent.id","EQ_S_resource"}, new String[]{menuId,ResourceType.Function.getValue()},"sort_asc");
		
	}
	@Override
	public boolean deleteMenu(Long menuId) {
		long childrenSize=functionPermissionDao.getChildrenSize(menuId);
		if(childrenSize>0)
			return false;
		functionPermissionDao.delete(menuId);
		return true;
	}
	

}
