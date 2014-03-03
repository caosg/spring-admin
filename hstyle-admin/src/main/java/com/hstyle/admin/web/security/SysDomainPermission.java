/**
 * 
 */
package com.hstyle.admin.web.security;

import java.util.Set;

import org.apache.shiro.authz.permission.DomainPermission;
import org.apache.shiro.util.StringUtils;

/**
 * @author Administrator
 *
 */
public class SysDomainPermission extends DomainPermission {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6501476884366919254L;
	public SysDomainPermission(String domain,String actions, String targets){
		this(domain, StringUtils.splitToSet(actions, SUBPART_DIVIDER_TOKEN),  StringUtils.splitToSet(targets, SUBPART_DIVIDER_TOKEN));
	}
	public SysDomainPermission(String domain, Set<String> actions, Set<String> targets){
		this.setParts(domain, actions, targets);
	}

}
