/**
 * 
 */
package com.hstyle.admin.dao;

import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.framework.dao.core.AdvancedDao;

/**
 * 功能权限管理dao
 * @author Administrator
 *
 */
public interface FunctionPermissionDao extends AdvancedDao<FunctionPermission, Long> {
    /**
     * 获得菜单子节点数量
     * @param menuId
     * @return
     */
    Long getChildrenSize(Long menuId);
}
