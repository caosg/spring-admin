/**
 * 
 */
package com.hstyle.admin.web.controller.sys;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.JavaType;
import com.hstyle.admin.common.UserContext;
import com.hstyle.admin.model.FileUploadBean;
import com.hstyle.admin.model.FunctionPermission;
import com.hstyle.admin.model.Property;
import com.hstyle.admin.service.FunctionPermissionService;
import com.hstyle.admin.service.support.SystemUtils;
import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;
import com.hstyle.admin.web.security.IncorrectCaptchaException;
import com.hstyle.admin.web.utils.AjaxUtils;
import com.hstyle.framework.exception.ApplicationException;
import com.hstyle.framework.mapper.JsonMapper;

/**
 * 系统登录、菜单等控制器
 * @author Administrator
 *
 */
@Controller
public class SysController extends BaseController{
	
	@Autowired
	private FunctionPermissionService functionPermissionService;
    /**
     * 登录成功
     * @return msg
     * @throws IOException 
     */
	@RequestMapping("login")
    public String login(@ModelAttribute("ajaxRequest") boolean ajaxRequest,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Msg msg=success();
		String authExceptionName=(String) request.getAttribute(FormAuthenticationFilter 
    			      .DEFAULT_ERROR_KEY_ATTRIBUTE_NAME); 
		logger.info("---login------ ajaxRequest:{}------",ajaxRequest);
    	if( authExceptionName != null ){
    		if(authExceptionName.equals(UnknownAccountException.class.getName()) || 
    				authExceptionName.equals(IncorrectCredentialsException.class.getName())){ 
    			msg=fail("账号或密码错误！"); 
    		}else if( authExceptionName.equals(IncorrectCaptchaException.class.getName())){ 
    			msg=fail("验证码错误！"); 
    	    }
    		else if( authExceptionName.equals(DisabledAccountException.class.getName())){ 
    			msg=fail("账号已被禁用！"); 
    	    }else{ 
    		   msg=fail("登录异常 !") ; 
    		} 
    		
    	}
    	if(ajaxRequest){
			
			response.getWriter().write(JsonMapper.nonDefaultMapper().toJson(msg));
			return null;
		}else {
			return "redirect:logout.do";
		}
    	
    }
	@RequestMapping("main")
	public String main(Model model,HttpServletRequest request){
		if (!SystemUtils.isAuthenticated()) {			
			return "redirect:index.jsp";
		} else {
			
			UserContext userContext = SystemUtils.getUserContext();			
			model.addAttribute("usercontext", userContext);
			model.addAttribute("userOpts", JsonMapper.nonEmptyMapper().toJson(userContext.getOpts()));	
			model.addAttribute("userMenus",JsonMapper.nonEmptyMapper().toJson(getFirstMenus(userContext)));
			request.getSession().setAttribute("loginName", userContext.getUser().getLoginName());
			logger.info("---user:{} login is succesful",userContext.getUser().getLoginName());
			return "main";
			
		}
	}
	private List<FunctionPermission> getFirstMenus(UserContext userContext){
		List<FunctionPermission> firstMenus=userContext.getChildMenu(-1L);
		for(FunctionPermission menu:firstMenus){
			
			menu.setChildren(null);
		}
		return firstMenus;
	}
	/** 
	 * 认证失败
	 * @return
	 * @throws IOException 
	 * @throws ApplicationException 
	 */
	@RequestMapping("unauthorized")	
	public  String authorizedFail(@ModelAttribute("ajaxRequest") boolean ajaxRequest,HttpServletResponse response) throws ApplicationException {
		logger.info("---unauthorized----ajaxrequest:{} ------",ajaxRequest);
		if(ajaxRequest){
			
			  throw new ApplicationException("权限不足");
			//response.getWriter().write(JsonMapper.nonDefaultMapper().toJson(fail("会话已失效，请退出重新登录！")));
			//return null;
		}else {
			return "redirect:index.jsp";
		}
		
	}
	/**
	 * 获取登录用户的子菜单
	 * @param id 父节点id
	 * @return
	 */
	@RequestMapping("getUserMenu")
	public @ResponseBody Msg getMenuChildren(@RequestParam String id){
		Long longId = 0L;		
		List<FunctionPermission> children;
		if (StringUtils.isEmpty(id)) {			
			longId = -1L;
		} else {
			longId = Long.parseLong(id);
		}
		logger.info("parentId:{}",longId);
		UserContext userContext = SystemUtils.getUserContext();
		
		children=userContext.getChildMenu(longId);
		return success(children);
	}
	
	@RequestMapping("logout")
	public String logout(){
		
		Subject subject = SecurityUtils.getSubject();
		if(subject!=null){		
		  subject.logout();
		}
		return "redirect:/index.jsp";
	}
	
	/**
	 * 文件上传，默认传到uplaod目录
	 * @param uploadBean
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping("upload")
	public @ResponseBody Msg uploadFile(FileUploadBean fileUploadBean,HttpServletRequest request){
		
		CommonsMultipartFile file=fileUploadBean.getFileData();
		logger.info("upload files is starting ,file size:{}...file origin name:{}...file updated name:{}",file.getSize(),file.getOriginalFilename(),fileUploadBean.getFileName());
		//获得上传目录绝对路径,默认放在当前应用upload目录下
		String filePath = request.getSession().getServletContext().getRealPath("") + "/upload/"+(StringUtils.isEmpty(fileUploadBean.getTargetPath())?"":fileUploadBean.getTargetPath()+"/");
		logger.info(" upload to real path:{},fileName:{}",filePath,fileUploadBean.getFileName());
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath+ fileUploadBean.getFileName()));
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("file upload is erroring ...");
			return fail("上传失败");
		}
		return success();
	}
	
	@RequestMapping("dynaform")
	public @ResponseBody Msg getDynaFormData(HttpServletRequest request){
		String propertiesJson=request.getParameter("properties");
		logger.info("properties json data:{}",propertiesJson);
		JsonMapper jsonMapper=JsonMapper.nonDefaultMapper();
		JavaType beanListType = jsonMapper.createCollectionType(List.class, Property.class);
		List<Property> properties=jsonMapper.fromJson(propertiesJson, beanListType);
		for(Property property:properties){
			logger.info("property id:{},value:{}",property.getId(),property.getValue());
		}
		return success();
	}
	@ModelAttribute
	public void ajaxAttribute(WebRequest request, Model model) {
		model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
	}
	
    
}
