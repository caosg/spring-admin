/**
 * 
 */
package com.hstyle.admin.web.controller.sys;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hstyle.admin.model.User;
import com.hstyle.admin.service.UserService;
import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;
import com.hstyle.framework.exception.ApplicationException;

/**
 * 用户管理spring-mvc前端控制类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	/**
	 * 新增用户
	 * @param user
	 * @return
	 * @throws ApplicationException 
	 */
	@RequestMapping("add")
	@ResponseBody
	public Msg addUser(@Valid User user,@RequestParam(required=false) List<Long> deptIds) throws ApplicationException{
		userService.newUser(user,deptIds);		
		return success("保存用户成功");
	}
	/**
	 * 编辑用户信息
	 * @param user
	 * @param deptIds 兼职部门
	 * @return
	 * @throws ApplicationException 
	 */
	@RequestMapping("edit")
	@ResponseBody
	public Msg editUser(@Valid @ModelAttribute("user") User user,@RequestParam(required=false) List<Long> deptIds) throws ApplicationException{
		if(deptIds!=null)
		  logger.info("---兼职部门数量:{}---",deptIds.size());		
		userService.editUser(user, deptIds);		
		return success("保存用户成功");
	}
    /**
     * 批量删除
     * @param ids 自行绑定表单中的checkBox ids到Lis集合中
     * @return msg
     */
    @RequestMapping("delete")
    @ResponseBody
    public Msg deleteUser(@RequestParam("ids")List<Long> ids) {
    	logger.info("delete user ids:{}",ids);
    	userService.deleteAll(ids);
    	return success("删除用户成功");
	}
    /**
     * 使用@ModelAttribute, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此本方法在该方法中执行.
     * @param id
     * @return
     */
    @ModelAttribute("user")
    public User bindUser(@RequestParam(value = "id", required = false)String id){
         User user = new User();
		if (StringUtils.isNotEmpty(id)) {
			user=userService.get(Long.parseLong(id));
		}
		return user;
    }
    
	/**
	 * 当前用户修改密码.修改成功返回"true"否则返回"false"
	 * 
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * 
	 * @return String
	 */
	@ResponseBody
	@RequestMapping("/changePassword")
	public Msg changePassword(@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword) {
		
		if (userService.updatePassword(oldPassword, newPassword)) {
			return success();
		}
		
		return fail("旧密码输入错误");
		
	}
	/**
	 * 密码重置
	 * @param ids 用户id集合
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetPassword")
	public Msg resetPassword(@RequestParam List<Long> ids){
		if(userService.resetPassword(ids))
			return success();
		return fail("重置密码失败");
		
	}
	
	/**
	 * 查询用户拥有的所有角色
	 * @param userID 用户id
	 * @return 角色集合
	 */
	@RequestMapping("roles")
	@ResponseBody
	public Msg getUserRoles(@RequestParam Long userId){
		return success(userService.getUserRoles(userId));
	}
	
	/**
	 * 分配角色
	 * @param user
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/add-role")
	@ResponseBody
	public Msg assginRole(@ModelAttribute("user") User user,@RequestParam List<Long> roleIds){
		userService.addRole(user, roleIds);
		return success();
	}
	
	/**
	 * 删除分配的角色
	 * @param user
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/remove-role")
	@ResponseBody
	public Msg removeRole(@ModelAttribute("user") User user,@RequestParam List<Long> roleIds){
		userService.removeRole(user, roleIds);
		return success();
	}
	/**
	 * rest方式外部登录
	 * @param userName
	 * @param password
	 * @return msg
	 */
	@RequestMapping("/login")
	@ResponseBody
    public Msg login(@RequestParam String userName,@RequestParam String password){
		Msg msg=new Msg();
    	User user=userService.getUserByLonginName(userName);
    	if(user==null)
    		return fail("用户不存在");
    	String md5Password = new SimpleHash("MD5",password).toHex();
    	if(md5Password.equals(user.getPassword()))
    	{
    		msg.setSuccess(true);
    		msg.setData(user);
    		return  msg;
    	}
    	else 
			return fail("用户密码错误");
		
    	
    }
}
