/**
 * 
 */
package com.hstyle.admin.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.DomainPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hstyle.admin.common.UserContext;
import com.hstyle.admin.model.DataPermission;
import com.hstyle.admin.model.DataTarget;
import com.hstyle.admin.model.Role;
import com.hstyle.admin.model.User;
import com.hstyle.admin.service.UserService;
import com.hstyle.framework.utils.CollectionUtils;

/**
 * 登录安全验证、权限获取
 * 
 * @author Administrator
 * 
 */
public class UserSecurityRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory
			.getLogger(UserSecurityRealm.class);
	@Autowired
	private UserService userService;
	private String defaultPermissions;
	private String defaultRoles;

	public UserSecurityRealm() {
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		String username = usernamePasswordToken.getUsername();

		if (username == null) {
			throw new AccountException("用户名不能为空");
		}
		logger.info("Authenticate user userName:{} and password:{}", username,
				new String(usernamePasswordToken.getPassword()));
		User user = userService.getUserByLonginName(username);

		if (user == null) {
			logger.info("user is null ---");
			throw new UnknownAccountException("用户不存在");
		}
		if (!user.isEnabled()) {
			logger.info("user is not enabled ---");
			throw new DisabledAccountException("用户已被禁用");
		}
		UserContext userContext = new UserContext(user);
		return new SimpleAuthenticationInfo(userContext, user.getPassword(),
				getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		UserContext userContext = (UserContext) principals
				.getPrimaryPrincipal();
		Assert.notNull(userContext, "找不到principals中的UserContext");
		logger.info("----authorize user:{}", userContext.getUser()
				.getUserName());
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 登录成功后加载当前用户上下文环境变量
		UserContext userContext1 = userService.initUserContext(userContext
				.getUser().getId());

		userContext.setUser(userContext1.getUser());
		userContext.setFunctions(userContext1.getFunctions());
		userContext.setDataPermissions(userContext1.getDataPermissions());
		userContext.setMenus(userContext1.getMenus());
		userContext.setOpts(userContext1.getOpts());
		addRoles(info, userContext.getUser().getRoles());
		addFunctionPermissions(info, userContext);
		addDataPermissions(info, userContext);
		return info;
	}

	/**
	 * 通过组集合，将集合中的role字段内容解析后添加到SimpleAuthorizationInfo授权信息中
	 * 
	 * @param info
	 *            SimpleAuthorizationInfo
	 * @param groupsList
	 *            组集合
	 */
	private void addRoles(SimpleAuthorizationInfo info, Collection<Role> roles) {

		// 解析当前用户组中的role
		List<String> temp = CollectionUtils.extractToList(roles, "code", true);
		List<String> roleNames = getValue(temp, "roles\\[(.*?)\\]");

		// 添加默认的roles到roels
		String[] dRoles = StringUtils.split(defaultRoles, ",");
		if (ArrayUtils.isNotEmpty(dRoles)) {
			CollectionUtils.addAll(roleNames, dRoles);
		}
		// 将当前用户拥有的roles设置到SimpleAuthorizationInfo中
		info.addRoles(roleNames);

	}

	/**
	 * 通过功能权限集合，将集合中的expression字段内容解析后添加到SimpleAuthorizationInfo授权信息中
	 * 
	 * @param info
	 *            SimpleAuthorizationInfo
	 * @param userContext
	 *            用户上下文
	 */
	private void addFunctionPermissions(SimpleAuthorizationInfo info,
			UserContext userContext) {
		List<String> permissions = new ArrayList<String>();
		if (userContext.getUser().isAdmin()) {
			permissions.add("*");
		} else {
			// 解析当前用户资源中的permissions
			List<String> temp = CollectionUtils.extractToList(
					userContext.getFunctions(), "expression", true);
			permissions = getValue(temp, "perms\\[(.*?)\\]");
			// 添加默认的permissions到permissions
			String[] dPermissions = StringUtils.split(defaultPermissions, ",");
			if (ArrayUtils.isNotEmpty(dPermissions)) {
				CollectionUtils.addAll(permissions, dPermissions);
			}
		}

		// 将当前用户拥有的permissions设置到SimpleAuthorizationInfo中
		info.addStringPermissions(permissions);

	}

	/**
	 * 通过数据权限集合，将集合中的expression字段内容解析后添加到SimpleAuthorizationInfo授权信息中
	 * 
	 * @param info
	 *            SimpleAuthorizationInfo
	 * @param userContext
	 *            用户上下文
	 */
	private void addDataPermissions(SimpleAuthorizationInfo info,
			UserContext userContext) {
		if (userContext.getUser().isAdmin()) return;
		List<DataPermission> userDataPermissions = userContext
				.getDataPermissions();
		if(userDataPermissions==null)
			return;
		for (DataPermission dataPermission : userDataPermissions) {
			if (dataPermission.getType().equals("action")) {
				String domain = dataPermission.getResource();
				String action = dataPermission.getAction();
				Set<DataTarget> targets = dataPermission.getTargets();
				if(targets==null)
					continue;
				DomainPermission domainPermission = new SysDomainPermission(
						domain, action, CollectionUtils.extractToString(
								targets, "value", ","));
				info.addStringPermission("DP"+dataPermission.getExpression());
				userContext.getDomains().put(dataPermission.getExpression(), domainPermission);
				logger.info("----permission expression={},action={},targets={}",dataPermission.getExpression(),action,CollectionUtils.extractToString(
								targets, "value", ","));
			}
		}
		

	}

	/**
	 * 通过正则表达式获取字符串集合的值
	 * 
	 * @param obj
	 *            字符串集合
	 * @param regex
	 *            表达式
	 * 
	 * @return List
	 */
	private List<String> getValue(List<String> obj, String regex) {

		List<String> result = new ArrayList<String>();

		if (CollectionUtils.isEmpty(obj)) {
			return result;
		}

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(StringUtils.join(obj, ","));

		while (matcher.find()) {
			result.add(matcher.group(1));
		}

		return result;
	}

	public void setDefaultPermissions(String defaultPermissions) {
		this.defaultPermissions = defaultPermissions;
	}

	public void setDefaultRoles(String defaultRoles) {
		this.defaultRoles = defaultRoles;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
