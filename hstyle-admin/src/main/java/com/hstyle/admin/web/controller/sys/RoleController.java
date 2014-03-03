/**
 * 
 */
package com.hstyle.admin.web.controller.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hstyle.admin.model.DataPermission;
import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.model.Role;
import com.hstyle.admin.model.User;
import com.hstyle.admin.service.RoleService;
import com.hstyle.admin.web.controller.AllTree;
import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.exception.ApplicationException;

/**
 * 角色管理web控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	@Autowired
	private RoleService roleService;
	
    /**
     * 分页查询所有角色
     * @param request
     * @return
     * @throws ApplicationException 
     */
    @RequestMapping("list")
    @ResponseBody
	public Page<Role> list(HttpServletRequest request) throws ApplicationException{
    	logger.info("---query roles----");
		PageRequest pageRequest=buildPageRequest(request);
		List<PropertyFilter> filters=buildFilterProperties(request);
		return roleService.findPage(pageRequest, filters);
	}
    
    /**
     * 添加角色
     * @param role
     * @return 
     */
    @RequestMapping("add")
    @ResponseBody
    public Msg add(@Valid Role role){
    	roleService.add(role);
    	return success("添加成功");
    }
    
    @ModelAttribute("role")
    public Role bindModel(@RequestParam(value="id",required=false) Long id){
    	Role role=new Role();
    	if(id!=null){
    		role=roleService.get(id);
    	}
    	return role;
    }
    
    /**
     * 编辑角色
     * @param role
     * @return
     */
    @RequestMapping("edit")
    @ResponseBody
    public Msg edit(@Valid @ModelAttribute("role") Role role){

    	roleService.update(role);
    	return success("保存成功");
    }
    
    /**
     * 批量删除
     * @param ids 自行绑定表单中的checkBox ids到Lis集合中
     * @return msg
     */
    @RequestMapping("delete")
    @ResponseBody
    public Msg delete(@RequestParam("ids")List<Long> ids) {
    	logger.info("delete role ids:{}",ids);
    	roleService.deleteAll(ids);
    	return success("删除角色成功");
	}
  
    /**
     * 查询角色已分配菜单功能
     * @param roleId 角色id
     * @param id 功能id
     * @return
     */
    @RequestMapping("functions")
    @ResponseBody
    public AllTree getFunctions(@RequestParam(value = "roleId", required = false) Long roleId,@RequestParam String id ){
    	logger.info("get role:{} all functions------------",roleId);
    	List<FunctionPermission> roleFunctions=roleService.hasFunctionPermissions(roleId,id);
    	treeExpanded(roleFunctions);
    	return new AllTree(true, roleFunctions);
    }
    private  void treeExpanded(List<FunctionPermission> roleFunctions){
    	//结合extjs树要求，
    	for(FunctionPermission functionPermission:roleFunctions){
    		if(functionPermission.getResource().equals("1")&&functionPermission.getLeaf())
        		functionPermission.setLeaf(false);
    		if(functionPermission.getResource().equals("1"))
    			functionPermission.setExpanded(true);
    		if(functionPermission.getChildren().size()>0)
    			treeExpanded(functionPermission.getChildren());
    	}
    }
    /**
     * 分配功能权限
     * @param role
     * @param permissionIds
     * @return
     */
    @RequestMapping("save-rolefunc")
    @ResponseBody
    public Msg assignFunctions(@ModelAttribute("role") Role role,@RequestParam("permissionIds") List<Long> permissionIds){
    	roleService.assignFunctionPermissions(role, permissionIds);
    	return success();
    }
    
    /**
     * 查询角色已分配数据权限
     * @param roleId
     * @param id
     * @return
     */
    @RequestMapping("datas")
    @ResponseBody
    public AllTree getPermissions(@RequestParam(value = "roleId", required = false) Long roleId,@RequestParam String id ){
    	List<DataPermission> roleDatas=roleService.hasDataPermissions(roleId, id);
    	//结合extjs树要求，全部展开便于前端页面展现
    	for(DataPermission dataPermission:roleDatas){
    		if(dataPermission.getType().equals("domain"))
    			dataPermission.setExpanded(true);
    		if(dataPermission.getType().equals("operation"))
    			dataPermission.setLeaf(true);
    	}
    	return new AllTree(true, roleDatas);
    }
    
    /**
     * 分配数据权限
     * @param role
     * @param permissionIds
     * @return
     */
    @RequestMapping("save-roleData")
    @ResponseBody
    public Msg assignDatas(@ModelAttribute("role") Role role,@RequestParam(value="permissionIds",required=false) List<Long> permissionIds){
    	logger.info("9999999999999999999999999");
    	roleService.assignDataPermisssions(role, permissionIds);
    	return success();
    }
    /**
     * 查询拥有该角色的人员
     * @param roleId 角色id
     * @return users
     */
    @RequestMapping("users")
    @ResponseBody
    public Msg getRoleUsers(@RequestParam(value = "roleId") Long roleId){
    	
    	List<User> users=roleService.getRoleUsers(roleId);
    	logger.info("---role:{} has users size={}-------",roleId,users.size());
    	return success(users);
    }
    
    /**
     * 给角色添加用户
     * @param role
     * @param userIds
     * @return
     */
    @RequestMapping("add-users")
    @ResponseBody
    public Msg addUsers(@ModelAttribute("role") Role role,@RequestParam("userIds") List<Long> userIds){
    	roleService.addUsers(role, userIds);
    	return success();
    }
    
    /**
     * 删除角色已分配的用户
     * @param role
     * @param userIds
     * @return
     */
    @RequestMapping("remove-users")
    @ResponseBody
    public Msg removeUsers(@ModelAttribute("role") Role role,@RequestParam("userIds") List<Long> userIds){
    	roleService.deleteUsers(role, userIds);
    	return success();
    }
    /**
     * 导出excel到http resonse
     * @param response
     */
    @RequestMapping("export")
    public void exportExcel(HttpServletResponse response){
    	List<Role> roles=roleService.findAll();
    	Map<String,List<Role>> data=new HashMap<String,List<Role>>();
    	data.put("role", roles);
    	this.expExcel(data, "role.xls", response);
    }
}
