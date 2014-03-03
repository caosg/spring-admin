/**
 * 
 */
package com.hstyle.admin.web.controller.sys;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hstyle.admin.model.Dept;
import com.hstyle.admin.model.User;
import com.hstyle.admin.service.DeptService;
import com.hstyle.admin.service.UserService;
import com.hstyle.admin.service.support.SystemUtils;
import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.dao.core.PropertyType;

/**
 * 部门管理前端spring-mvc控制类
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/dept")
public class DeptController extends BaseController{
	
	@Autowired
	private DeptService deptService;
	@Autowired
	private UserService userService;
	
	@ModelAttribute("dept")
	public Dept bindDept(@RequestParam(value="id",required=false) Long id){
		Dept dept=new Dept();
		if(id!=null)
			dept=deptService.get(id);
		return dept;
	}
	
    /**
     * 添加编辑部门信息
     * @param dept
     * @return msg
     */
    @RequestMapping("edit")
	public @ResponseBody Msg edit(@Valid @ModelAttribute("dept") Dept dept) {
    	logger.info("---save dept --");
		deptService.save(dept);    	
		return success(dept);
	}
    
    /**
     * 添加部门
     * @param dept
     * @return
     */
    @RequestMapping("add")
    public @ResponseBody Msg add(@Valid Dept dept){
    	deptService.addDept(dept);
    	return success(dept);
    }
    
    /**
     * 查询部门所有下级部门
     * @param deptCode
     * @return
     */
    @RequestMapping("allChildren")
    public @ResponseBody Msg getAllChildren(@RequestParam(required=true) String deptCode){
    	return success(deptService.getAllChildren(deptCode));
    }
    /**
     * 
     * @param deptCode
     * 普通获取改节点下面的所有部门，不记录在系统的访问日志中 
     * @return
     */
    @RequestMapping("commonAllChildren")
    public @ResponseBody Msg getCommmonAllChildren(@RequestParam(required=true) String deptCode){
    	return success(deptService.getAllChildren(deptCode));
    }
    /**
     * 查询子部门
     * @param id
     * @return
     */
    @RequestMapping("children")
    public @ResponseBody Msg getChildren(@RequestParam String id){
    	Long longId ;
		List<Dept> children;
		if (StringUtils.isEmpty(id)) {
			longId = Dept.ROOT_ID;
		} else {
			longId = Long.parseLong(id);
		}
		
		children=deptService.getChildren(longId);
		return success(children);
    }
    /**
     * 选择部门
     * @param id
     * @return
     */
    @RequestMapping("selectDept")
    public @ResponseBody Msg selectChildren(@RequestParam String id){
    	Long longId ;
		List<Dept> children;
		if (StringUtils.isEmpty(id)) {
			longId = Dept.ROOT_ID;
		} else {
			longId = Long.parseLong(id);
		}
		//数据权限使用示例
		if(SystemUtils.isDataPermitted("dept:query:IN")){
			Set<String> targets=SystemUtils.getTargets("dept:query:IN");
			if(targets!=null){
				for(String target:targets){
		              System.out.println(target);
		              //加入业务代码处理
		              //........
				}
			}
		}
		children=deptService.getChildren(longId);
		return success(children);
    }
    /**
     * 普通获取改节点下面的孩子部门，不记录在系统的访问日志中 
     * @param id
     * @return
     */
    @RequestMapping("commonChildren")
    public @ResponseBody Msg getCommonChildren(@RequestParam String id){
    	Long longId ;
		List<Dept> children;
		if (StringUtils.isEmpty(id)) {
			longId = Dept.ROOT_ID;
		} else {
			longId = Long.parseLong(id);
		}
		//数据权限使用示例
		if(SystemUtils.isDataPermitted("dept:query:IN")){
			Set<String> targets=SystemUtils.getTargets("dept:query:IN");
			if(targets!=null){
				for(String target:targets){
		              System.out.println(target);
		              //加入业务代码处理
		              //........
				}
			}
		}
		children=deptService.getChildren(longId);
		return success(children);
    }
    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public @ResponseBody Msg delete(@RequestParam Long id){
		if (id==null||id==Dept.ROOT_ID) {
			return fail("根级目录不允许删除");
		}
		logger.info("delete dept id:{}",id);

		deptService.delete(id);
		return success("删除成功");
    }
    /**
     * 根据部门id查询部门人员，包含所有子级部门
     * @param id 部门id
     * @param request
     * @return
     */
    @RequestMapping("users")
    public @ResponseBody Page<User> getUsers(@RequestParam String deptCode,HttpServletRequest request){
    	logger.info("qurey users by deptCode:{}",deptCode);

		if (StringUtils.isEmpty(deptCode)) {
			return null;
		} 	
		
		PageRequest pageRequest=buildPageRequest(request);	
		Page<User> users=userService.getUsersByDept(pageRequest, deptCode);
		return users;
    }
    /**
     * 根据部门id查询部门人员，包含所有子级部门
     * @param request
     * @return
     */
    @ResponseBody 
    @RequestMapping(value={"queryuser","getuser"},method=RequestMethod.GET)
    public Page<User> getUsersFilter(@RequestParam String deptCode,HttpServletRequest request){
    	logger.info("qurey users by filter deptCode:{}",deptCode);
    	if (StringUtils.isEmpty(deptCode)) {
    		deptCode="0";
    	}
    	//部门全路径，查询所有本部门及下级部门人员
    	List<PropertyFilter> filters=buildFilterProperties(request);
    	PropertyFilter deptFilter=new PropertyFilter("RLIKE",PropertyType.S,new String[]{"dept.code"},deptCode);
    	filters.add(deptFilter);
		PageRequest pageRequest=buildPageRequest(request);		
		Page<User> users=userService.findPage(pageRequest, filters);
		return users;
    } 
}
