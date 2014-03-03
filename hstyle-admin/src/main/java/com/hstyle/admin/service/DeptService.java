/**
 * 
 */
package com.hstyle.admin.service;

import java.util.List;

import com.hstyle.admin.model.Dept;
import com.hstyle.framework.service.GenericService;

/**
 * @author Administrator
 * 
 */
public interface DeptService extends GenericService<Dept, Long> {
	/**
	 * 根据父部门代码查找子部门,排除删除状态
	 * @param parentId
	 * @return
	 */
	List<Dept> getChildren(Long parentId);
	
	/**
	 * 查询部门所有子级及子子级部门,排除删除状态
	 * @param parentDeptCode 父级部门code
	 * @return
	 */
	List<Dept> getAllChildren(String parentDeptCode);
	
	/**
	 * 添加部门，并处理部门全路径代码
	 * @param dept
	 */
	void addDept(Dept dept);
	
	void editDept(Dept dept);
}
