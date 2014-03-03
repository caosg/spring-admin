/**
 * 
 */
package com.hstyle.admin.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.shiro.authz.permission.DomainPermission;

import com.google.common.collect.Lists;
import com.hstyle.admin.model.DataPermission;
import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.model.User;

/**
 * 用户上下文，保存用户登录成功后的用户信息、功能权限、数据权限、部门
 * @author Administrator
 *
 */
public class UserContext implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7435795236049277841L;
	//当前登录用户
    private User user;
    //当前用户的菜单集合
    private List<FunctionPermission> menus;
    //当前用户的操作集合
    private List<FunctionPermission> opts;
    //当前用户的的所有功能权限
    private List<FunctionPermission> functions;
    //当前用户的数据权限
    private List<DataPermission>  dataPermissions;
    
    private Map<String, DomainPermission> domains=new HashMap<String, DomainPermission>();    
    public UserContext(){}
    
    public UserContext(User user){
    	this.user=user;
    }
    
	/**
	 * 获取当前登录的用户
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * 设置当前登录用户
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 获取当前用户的菜单集合
	 * @return the menus
	 */
	public List<FunctionPermission> getMenus() {
		return menus;
	}
	/**
	 * 设置当前用户的菜单
	 * @param menus
	 */
	public void setMenus(List<FunctionPermission> menus) {
		this.menus = menus;
	}
	/**
	 * 根据菜单Id，查找当前菜单
	 * @param id
	 * @return
	 */
	public FunctionPermission getCurrentMenu(Long id){
		if(menus==null) return null;
		for(FunctionPermission menu:menus){
			if(menu.getId()==id)
				return menu;
		}
		return null;
	}
	/** 根据当前菜单返回子节点
	 * @param parentId
	 * @return
	 */
	public List<FunctionPermission> getChildMenu(Long parentId){
		List<FunctionPermission> children=Lists.newArrayList();
		if(menus==null) return null;
		for(FunctionPermission menu:menus){			
			if(menu.getParent()!=null&&menu.getParent().getId().longValue()==parentId.longValue()){				
				children.add(menu);
			}
		}
		return children;
	}
	/**
	 * 获取当前用户的功能权限
	 * @return the functions
	 */
	public List<FunctionPermission> getFunctions() {
		return functions;
	}
	/**
	 * @param functions
	 */
	public void setFunctions(List<FunctionPermission> functions) {
		this.functions = functions;
	}
	
	/**
	 * 获取当前用户的数据权限集合
	 * @return the dataPermissions
	 */
	public List<DataPermission> getDataPermissions() {
		return dataPermissions;
	}

	/**
	 * 设置当前用户数据权限
	 * @param dataPermissions the dataPermissions to set
	 */
	public void setDataPermissions(List<DataPermission> dataPermissions) {
		this.dataPermissions = dataPermissions;
	}
    @Override
	public String toString() {
		
		return user.getUserName();
	}
    /**
	 * 重载equals,只计算loginName;
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(user).toHashCode();
	}

	/**
	 * 重载equals,只比较loginName
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) {
		    return false;
		}
		UserContext rhs = (UserContext) obj;
		return new EqualsBuilder()
		                .appendSuper(super.equals(obj))
		                .append(user.getLoginName(), rhs.user.getLoginName())
		                 .isEquals();
		
	}

	/**
	 * @return the opts
	 */
	public List<FunctionPermission> getOpts() {
		return opts;
	}

	/**
	 * @param opts the opts to set
	 */
	public void setOpts(List<FunctionPermission> opts) {
		this.opts = opts;
	}

	/**
	 * @return the domains
	 */
	public Map<String, DomainPermission> getDomains() {
		return domains;
	}
    /**
     * 根据权限表达式获得权限对象
     * @param expression
     * @return
     */
    public DomainPermission getDomainPermission(String expression){
    	DomainPermission domainPermission=null;
    	if(domains!=null)
    		domainPermission= domains.get(expression);
    	return domainPermission;
    	
    }
  
	/**
	 * @param domains the domains to set
	 */
	public void setDomains(Map<String, DomainPermission> domains) {
		this.domains = domains;
	}
}
