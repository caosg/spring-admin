/**
 * 
 */
package com.hstyle.admin.service;

import java.util.Collection;
import java.util.List;

import com.hstyle.admin.model.DataPermission;
import com.hstyle.admin.model.DataTarget;
import com.hstyle.framework.service.GenericService;

/**
 * 数据权限服务
 * @author Administrator
 *
 */
public interface DataPermissionService extends GenericService<DataPermission, Long>{
	
    /**
     * 获得子分类
     * @param parentId 父id
     * @return
     */
    Collection<DataPermission> getChildren(Long parentId);
    
    /**
     * 获得数据权限
     * @param typeId 数据权限分类id
     * @return
     */
    List<DataPermission> findPermissionRules(Long typeId);
    
    /**
     * 保存操作目标
     * @param target
     */
    void saveTarget(DataTarget target);
    
    /**
     * 删除目标
     * @param ids
     */
    void removeTarget(List<Long> ids);
}
