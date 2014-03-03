/**
 * 
 */
package com.hstyle.admin.service.imp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.list.SetUniqueList;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.hstyle.admin.common.ResourceType;
import com.hstyle.admin.common.State;
import com.hstyle.admin.common.UserContext;
import com.hstyle.admin.dao.DeptDao;
import com.hstyle.admin.dao.FunctionPermissionDao;
import com.hstyle.admin.dao.RoleDao;
import com.hstyle.admin.dao.UserDao;
import com.hstyle.admin.model.DataPermission;
import com.hstyle.admin.model.Dept;
import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.model.Role;
import com.hstyle.admin.model.User;
import com.hstyle.admin.service.UserService;
import com.hstyle.admin.service.support.SystemUtils;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.exception.ApplicationException;
import com.hstyle.framework.service.DefaultServiceImp;
import com.hstyle.framework.utils.CollectionUtils;

/**
 * @author Administrator
 *
 */
@Service
public class UserServiceImp extends DefaultServiceImp<User, Long> implements UserService {
   
    private static String DEFAULT_PASSWORD="123456";
    
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private FunctionPermissionDao funcDao;
    @Autowired
    private DeptDao deptDao;

    
    public UserServiceImp() {
		
	}

    @Autowired
	public void setUseDao(UserDao useDao) {
		this.userDao = useDao;
		this.dao=useDao;
	}

	@Override
	public User getUserByLonginName(String loginName) {	
		log.info("-------根据登录名，查询用户 ----------------------");
		return userDao.findUniqueByProperty("loginName", loginName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserContext initUserContext(Long userId) {
		log.info("----------initial user context ----------------------");
		User user = userDao.get(userId);
		Assert.notNull(user, "user id:"+userId+" is null");
		//加载lazy
		
		userDao.initProxyObject(user.getRoles());
		//判断用户是否拥有超级管理员角色
		user.setAdmin(isAdmin(user.getRoles()));
		log.info("-----loading user's rols num is {} --user is admin:{}",user.getRoles().size(),user.isAdmin());
		List<FunctionPermission> functionPermissions=Lists.newArrayList();
		List<DataPermission>   dataPermissions=Lists.newArrayList();
		//是超级管理员，加载所有权限
		if(user.isAdmin()){		
			functionPermissions=funcDao.getAll();
		}else {
			//加载所有角色权限
			for (Role role : user.getRoles()) {		
				functionPermissions.addAll(role.getFunctPermissions());				
				dataPermissions.addAll(role.getDataPermissions());	
				log.info("------------- role:{} func num is {},data num is {}",role.getName(),role.getFunctPermissions().size(),role.getDataPermissions().size());
			}
			
		}
		
		log.info("---loading user's funcs num is={},datas num is={}-------------",functionPermissions.size(),dataPermissions.size());
		UserContext userContext=new UserContext(user);
		//去掉重复元素
		userContext.setFunctions(SetUniqueList.decorate(functionPermissions));
		//加载数据权限
		userContext.setDataPermissions(SetUniqueList.decorate(dataPermissions));
		//加载用户菜单
		userContext.setMenus(getMenus(userContext.getFunctions()));
		log.info("--------- user's menu num is={}--------------",userContext.getMenus().size());
		//加载用户操作按钮
		userContext.setOpts(CollectionUtils.subtract(userContext.getFunctions(), userContext.getMenus()));
		return userContext;
	}
	/**
	 * 判断用户是否是超级角色
	 * @param roles
	 * @return true or false
	 */
	private boolean isAdmin(Collection<Role> roles){
		boolean isAdmin=false;
		for(Role role:roles){
			if(role.isAdmin()){
				isAdmin=true;
				break;
			}
		}
		return isAdmin;
	}
	//抽取菜单
	private List<FunctionPermission> getMenus(List<FunctionPermission> allFunctionPermissions){
		
		List<FunctionPermission> result=Lists.newArrayList();
		for(Iterator<FunctionPermission> iterator=allFunctionPermissions.iterator();iterator.hasNext();){
			FunctionPermission functionPermission=iterator.next();
			if(functionPermission.getResource()!=null&&functionPermission.getResource().equals(ResourceType.Menu.getValue())){
				result.add(functionPermission);
				iterator.remove();
			}
		}
		return result;
	}

	@Override
	public boolean updatePassword(String oldPassword, String newPassword) {
		User user=SystemUtils.getUserContext().getUser();
		oldPassword = new SimpleHash("MD5", oldPassword.toCharArray()).toString();
		if(user.getPassword().equals(oldPassword)) {
			String temp = new SimpleHash("MD5",newPassword).toHex();
			user.setPassword(temp);
			userDao.update(user);
			 
			return true;
		}
		
		return false;
	}

	@Override
	public Page<User> getUsersByDept(PageRequest pageRequest,String deptCode) {
		String hqlString="from User X where X.dept.code like ?";
		
		Page<User> page=userDao.findPageByQuery(pageRequest, hqlString, deptCode+"%");
		return page;
	}

	@Override
	public void newUser(User user,List<Long> deptIds) throws ApplicationException {
		if(user.getDeptId()!=null&&user.getDept()==null){
			Dept dept=new Dept();
			dept.setId(user.getDeptId());
			user.setDept(dept);
		}
		//判断登录账号是否唯一
		User entity=new User();
		entity.setLoginName(user.getLoginName());
		if(!userDao.isUnique(entity))
		   throw new ApplicationException("登录账号已存在！");
		if(deptIds!=null){
			List<Dept> depts=deptDao.get(deptIds);
			user.getDepts().addAll(depts);
		}
		//设置默认密码，md5加密
		String password = new SimpleHash("MD5", DEFAULT_PASSWORD).toHex();
		user.setPassword(password);
		user.setStatus(State.Enable.getValue());
		userDao.insert(user);
		
	}
	
	@Override
	public void editUser(User user, List<Long> deptIds) throws ApplicationException {
		if(user.getDeptId()!=null&&user.getDept().getId()!=user.getDeptId()){
			Dept dept=new Dept();
			dept.setId(user.getDeptId());
			user.setDept(dept);
		}
		//判断登录账号是否唯一
		User entity=new User();
		entity.setLoginName(user.getLoginName());

		//先移除已删除的部门
		for(Iterator<Dept> iterator=user.getDepts().iterator();iterator.hasNext();){
			Dept dept=iterator.next();
			if(deptIds==null)iterator.remove();
			else{
				if(!deptIds.contains(dept.getId()))
				iterator.remove();
			}
		}
		//增加新增的部门
		List<Dept> newDepts=deptDao.get(deptIds);
		user.getDepts().addAll(newDepts);
		userDao.update(user);
		
	}

	@Override
	public void addRole(User user, List<Long> roleIds) {
		log.info("---user:{} add roles num {}",user.getUserName(),roleIds.size());
		List<Role> roles=roleDao.get(roleIds);
		user.getRoles().addAll(roles);
		
	}

	@Override
	public void removeRole(User user, List<Long> roleIds) {
		for(Long roleId:roleIds){
			for(Iterator<Role> iterator=user.getRoles().iterator();iterator.hasNext();){
				Role role=iterator.next();
				if(role.getId()==roleId)
					iterator.remove();
			}
		}
		log.info("---user:{} remove roles num {}",user.getUserName(),roleIds.size());
	}

	@Override
	public Collection<Role> getUserRoles(Long userId) {
		User user=userDao.get(userId);
		return user.getRoles();
	}

	@Override
	public boolean resetPassword(List<Long> userIds) {
		if(userIds==null||userIds.size()==0)
		  return false;
		List<User> users=userDao.get(userIds);
		String originalPassword = new SimpleHash("MD5",DEFAULT_PASSWORD).toHex();
		for(User user:users){
			user.setPassword(originalPassword);
			
		}
		
		return true;
	}

	
}
