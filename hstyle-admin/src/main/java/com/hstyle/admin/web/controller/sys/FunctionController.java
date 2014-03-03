/**
 * 
 */
package com.hstyle.admin.web.controller.sys;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.service.FunctionPermissionService;
import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;

/**
 * 菜单功能管理spring-mvc控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/menu")
public class FunctionController extends BaseController {
	@Autowired
	private FunctionPermissionService functionPermissionService;
    /**
     * 获取菜单功能
     * @param menuId
     * @return
     */
    @RequestMapping("functions")
    @ResponseBody
    public List<FunctionPermission> getFunctionsByMenu(@RequestParam String menuId){
    	if(StringUtils.isEmpty(menuId))
    		return null;
    	return functionPermissionService.getFunctions(menuId);
    }
    /**
     * 保存菜单功能
     * @param menu
     * @param parentId
     * @return
     */
    @RequestMapping("save")
    @ResponseBody
    public Msg save(@Valid FunctionPermission menu,@RequestParam Long parentId ){
    	if(parentId==null)
    		menu.setParent(null);
    	else {
			FunctionPermission parent=new FunctionPermission();
			parent.setId(parentId);
			menu.setParent(parent);
		}
    	functionPermissionService.save(menu);
    	return success(menu);
    }
    /**
     * 删除菜单
     * @param id
     * @return
     */
    @RequestMapping("deleteMenu")
    @ResponseBody
    public Msg deleteMenu(@RequestParam Long id){
    	boolean result=functionPermissionService.deleteMenu(id);
    	if(result)
    	    return success("删除成功");
    	else 
    		return fail("不允许删除，有关联数据");
    }
    /**
     * 获得子菜单
     * @param parentId
     * @return
     */
    @RequestMapping("getMenu")
    @ResponseBody
    public Msg getMenuChildren(@RequestParam(value="id") Long parentId){	
		List<FunctionPermission> children;
		if (parentId==null) 		
			parentId = -1L;
		
		logger.info("parentId:{}",parentId);
		children = functionPermissionService.getMenuChildren(parentId);
		return success(children);
    }
    /**
     * 删除功能
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteFunctions")
    public Msg deleteFunctions(@RequestParam List<Long> ids){
    	functionPermissionService.deleteAll(ids);
    	return success("删除成功");
    }
}
