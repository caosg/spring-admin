/**
 * 
 */
package com.hstyle.admin.web.controller.sys;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hstyle.admin.model.DataPermission;
import com.hstyle.admin.model.DataTarget;
import com.hstyle.admin.service.DataPermissionService;
import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;

/**
 * 数据权限spring-mvc前端控制器
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/permission")
public class DataPermissionController extends BaseController {
	@Autowired
	private DataPermissionService dataPermissionService;

	/**
	 * 查询子分类
	 * @param parentId
	 * @return
	 */
	@RequestMapping("children")
	@ResponseBody
	private Msg getChildren(@RequestParam(value = "id") Long parentId) {
		Collection<DataPermission> children=dataPermissionService.getChildren(parentId);
		//适应前端树的展现
		for(DataPermission dataPermission:children){			
			if(dataPermission.getType().equals("1"))
				dataPermission.setLeaf(true);
		}
		return success(children);
	}
	
	/**
	 * 保存数据权限
	 * @param dataPermission
	 * @param parentId
	 * @return
	 */
	@RequestMapping("save")
    @ResponseBody
    public Msg save(@Valid DataPermission dataPermission,@RequestParam Long parentId ){
		if(parentId==null)
			dataPermission.setParent(null);
    	else {
    		DataPermission parent=new DataPermission();
    		parent.setId(parentId);
    		dataPermission.setParent(parent);
    	}
		dataPermissionService.save(dataPermission);
		return success(dataPermission);
	}
	
	/**
	 * 保存目标
	 * @param dataPermission
	 * @param parentId
	 * @return
	 */
	@RequestMapping("saveTarget")
    @ResponseBody
    public Msg saveTarget(@Valid DataTarget target ){
		logger.info("----{}",target.getDataId());
		dataPermissionService.saveTarget(target);
		return success(target);
	}
	
	/**
	 * 删除数据权限
	 * @param id
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Msg delete(@RequestParam Long id){
		dataPermissionService.delete(id);
		return success();
	}
	
	/**
	 * 删除权限实例
	 * @param ids 
	 * @return
	 */
	@RequestMapping("remove-instance")
	@ResponseBody
	public Msg removeInstance(@RequestParam List<Long> ids){
		dataPermissionService.removeTarget(ids);
		return success();
	}
	/**
	 * 查询权限实例
	 * @param typeId 类型id
	 * @return
	 */
	@RequestMapping("rules")
	@ResponseBody
	public Set<DataTarget> findRules(@RequestParam Long typeId){
		DataPermission dataPermission=dataPermissionService.get(typeId);
		return dataPermission.getTargets();
	}
	
}
