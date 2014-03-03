/**
 * 
 */
package com.hstyle.admin.service.imp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstyle.admin.dao.DataPermissionDao;
import com.hstyle.admin.dao.FunctionPermissionDao;
import com.hstyle.admin.dao.RoleDao;
import com.hstyle.admin.dao.UserDao;
import com.hstyle.admin.model.DataPermission;
import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.model.Role;
import com.hstyle.admin.model.User;
import com.hstyle.admin.service.RoleService;
import com.hstyle.framework.service.DefaultServiceImp;

/**
 * @author Administrator
 * 
 */
@Service("roleService")
public class RoleServiceImp extends DefaultServiceImp<Role, Long> implements
		RoleService {

	private RoleDao roleDao;
	@Autowired
	private FunctionPermissionDao functionPermissionDao;
	@Autowired
	private DataPermissionDao dataPermissionDao;
	@Autowired
	private UserDao userDao;

	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
		this.dao = roleDao;
	}

	@Override
	public List<FunctionPermission> hasFunctionPermissions(Long roleId,
			String id) {
		List<FunctionPermission> allFunctions = functionPermissionDao
				.findByExpressions(new String[] { "EQ_L_parent.id" },
						new String[] { id }, "sort_asc");
		Role role = roleDao.get(roleId);

		if (role.getFunctPermissions() != null
				&& role.getFunctPermissions().size() > 0)
			checked(allFunctions, role.getFunctPermissions());
		return allFunctions;
	}

	/**
	 * 选中角色拥有的菜单功能
	 * 
	 * @param functionPermission
	 * @param roleFunctionPermissions
	 */
	private void checked(Collection<FunctionPermission> functionPermissions,
			Collection<FunctionPermission> roleFunctionPermissions) {
		for (FunctionPermission functionPermission : functionPermissions) {

			for (FunctionPermission roleFunctionPermission : roleFunctionPermissions) {
				if (functionPermission.getId() == roleFunctionPermission
						.getId()) {
					functionPermission.setRoleChecked(true);
				}
				if (functionPermission.getChildren() != null
						&& functionPermission.getChildren().size() > 0) {
					checked(functionPermission.getChildren(),
							roleFunctionPermissions);
				}
			}
		}
	}

	@Override
	public boolean assignFunctionPermissions(Role role, List<Long> funcIds) {
		if(funcIds!=null){
		log.info("-------save role funcs num {}--------", funcIds.size());
		// 先移除原先有现在没有的
		for (Iterator<FunctionPermission> iterator = role.getFunctPermissions()
				.iterator(); iterator.hasNext();) {
			FunctionPermission func = iterator.next();
			if (!funcIds.contains(func.getId()))
				iterator.remove();
		}
		List<FunctionPermission> assingFuncs = functionPermissionDao
				.get(funcIds);
		role.getFunctPermissions().addAll(assingFuncs);
		}else {
			if(role.getFunctPermissions().size()>0)
				role.getFunctPermissions().clear();
		}
		roleDao.update(role);
		return true;
	}

	@Override
	public List<DataPermission> hasDataPermissions(Long roleId, String nodeId) {
		List<DataPermission> allDatas = dataPermissionDao.findByExpressions(
				new String[] { "EQ_L_parent.id", "NE_S_type" }, new String[] {
						nodeId, "instance" }, "sort_asc");
		Role role = roleDao.get(roleId);
		if (role.getDataPermissions() != null
				&& role.getDataPermissions().size() > 0)
			dataChecked(allDatas, role.getDataPermissions());
		return allDatas;
	}

	private void dataChecked(Collection<DataPermission> dataPermissions,
			Collection<DataPermission> roleDataPermissions) {
		for (DataPermission dataPermission : dataPermissions) {

			for (DataPermission roleDataPermission : roleDataPermissions) {
				if (dataPermission.getId() == roleDataPermission.getId()) {
					dataPermission.setRoleChecked(true);
				}
				if (dataPermission.getChildren() != null
						&& dataPermission.getChildren().size() > 0) {
					dataChecked(dataPermission.getChildren(),
							roleDataPermissions);
				}
			}
		}
	}

	@SuppressWarnings("unused")
	@Override
	public boolean assignDataPermisssions(Role role, List<Long> dataIds) {
		
		if (dataIds != null) {
			log.info("-------save role dataIds num {}--------", dataIds.size());
			// 先移除原先有现在没有的
			for (Iterator<DataPermission> iterator = role.getDataPermissions()
					.iterator(); iterator.hasNext();) {
				DataPermission func = iterator.next();
				if (!dataIds.contains(func.getId()))
					iterator.remove();
			}
			List<DataPermission> assingDatas = dataPermissionDao.get(dataIds);
			role.getDataPermissions().addAll(assingDatas);
		} else {
			if(role.getDataPermissions().size()>0)
			   role.getDataPermissions().clear();
		}
		roleDao.update(role);
		return true;
	}

	@Override
	public List<User> getRoleUsers(Long roleId) {
		return roleDao.findByQueryNamed(Role.FindRoleUsers, roleId);
	}

	@Override
	public boolean addUsers(Role role, List<Long> userIds) {
		List<User> users = userDao.get(userIds);
		roleDao.initProxyObject(role.getUsers());
		role.getUsers().addAll(users);
		log.info("---add role[{}]'s users:{}", role.getName(), userIds);
		return true;
	}

	@Override
	public boolean deleteUsers(Role role, List<Long> userIds) {
		log.info("---remove role[{}]'s users:{}", role.getName(), userIds);
		roleDao.initProxyObject(role.getUsers());

		for (Long userId : userIds) {
			for (Iterator<User> iterator = role.getUsers().iterator(); iterator
					.hasNext();) {
				User user = iterator.next();
				if (user.getId() == userId)
					iterator.remove();
			}

		}
		return true;
	}

}
