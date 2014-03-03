/**
 * 
 */
package com.hstyle.admin.service;

import java.util.List;

import com.hstyle.admin.model.DataPermission;
import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.model.Role;
import com.hstyle.admin.model.User;
import com.hstyle.framework.service.GenericService;

/**
 * @author Administrator
 * 
 */
public interface RoleService extends GenericService<Role, Long> {

	/**
	 * 查询角色拥有的菜单功能树
	 * 
	 * @param roleId
	 *            角色id
	 * @param nodeId 父菜单id
	 * @return list<FunctionPermission>
	 **/
	List<FunctionPermission> hasFunctionPermissions(Long roleId, String nodeId);

	/**
	 * 给角色分配功能权限
	 * @param role 角色
	 * @param funcIds 功能权限id集合
	 * @return true：false
	 */
	boolean assignFunctionPermissions(Role role, List<Long> funcIds);
	
	/**
	 * 查询角色所拥有的数据权限树
	 * @param roleId 角色id
	 * @param nodeId 权限树父节点id
	 * @return
	 */
	List<DataPermission>  hasDataPermissions(Long roleId,String nodeId);
	
	/**
	 * 给角色分配数据权限
	 * @param role 角色
	 * @param dataIds 数据权限id集合
	 * @return true：false
	 */
	boolean assignDataPermisssions(Role role, List<Long> dataIds);
	
	/**
	 * 查询角色已分配给的所有人员
	 * @param roleId
	 * @return 
	 */
	List<User> getRoleUsers(Long roleId);
	
	/**
	 * 给角色添加用户
	 * @param role 角色
	 * @param userIds  用户id集合
	 * @return
	 */
	boolean addUsers(Role role,List<Long> userIds);
	
	/**
	 * 删除角色已分配用户
	 * @param role 角色
	 * @param userIds 用户id集合
	 * @return
	 */
	boolean deleteUsers(Role role,List<Long> userIds);
}
