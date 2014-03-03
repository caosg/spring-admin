/**
 * 
 */
package com.hstyle.admin.dao;

import com.hstyle.admin.model.Dept;
import com.hstyle.framework.dao.core.AdvancedDao;

/**
 * 部门管理dao
 * 
 * @author Administrator
 * 
 */
public interface DeptDao extends AdvancedDao<Dept, Long> {
	/**
	 * 获得直属子部门的数量,包括已删除的
	 * 
	 * @param parentId
	 * @return
	 */
	long getChlidNum(Long parentId);
}
