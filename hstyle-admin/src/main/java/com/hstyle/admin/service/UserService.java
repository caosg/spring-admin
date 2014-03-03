/**
 * 
 */
package com.hstyle.admin.service;

import java.util.Collection;
import java.util.List;

import com.hstyle.admin.common.UserContext;
import com.hstyle.admin.model.Role;
import com.hstyle.admin.model.User;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.exception.ApplicationException;
import com.hstyle.framework.service.GenericService;

/**
 * @author Administrator
 * 
 */
public interface UserService extends GenericService<User, Long> {

	/**
	 * 根据登录账号获取用户
	 * @param loginName
	 * @return user
	 */
	User getUserByLonginName(String loginName);
	
	/**
	 * 初始化用户上下文环境
	 * @param userId
	 * @return
	 */
	UserContext initUserContext(Long userId);
	
	/**
	 * 修改用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @return true or false
	 */
	boolean updatePassword(String oldPassword,String newPassword);
	
	/**
	 * 密码重置
	 * @param userIds 用户id集合
	 * @return true or false
	 */
	boolean resetPassword(List<Long> userIds);
	
	/**
	 * 根据部门id查询部门人员，包含所有下属部门
	 * @param pageRequest 分页参数
	 * @param deptCode 部门code
	 * @return page
	 */
	Page<User> getUsersByDept(PageRequest pageRequest,String deptCode);
	
	/**
	 * 新增用户
	 * @param user
	 * @param deptIds 兼职部门id集合
	 */
	void newUser(User user,List<Long> deptIds) throws ApplicationException;
	
	/**
	 * 编辑用户
	 * @param user
	 * @param deptIds 兼职部门id集合
	 */
	void editUser(User user,List<Long> deptIds) throws ApplicationException ;
	
	/**
	 * 查询用户角色
	 * @param userId
	 * @return 角色集合
	 */
	Collection<Role> getUserRoles(Long userId);
	
	/**
	 * 给用户分配角色
	 * @param user 用户
	 * @param roleIds 角色id集合
	 */
	void addRole(User user,List<Long> roleIds);
	
	/**
	 * 删除用户的角色
	 * @param user 用户
	 * @param roleIds 角色id集合
	 */
	void removeRole(User user,List<Long> roleIds);
}
