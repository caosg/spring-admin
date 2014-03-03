package com.hstyle.admin.web.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hstyle.admin.model.ModuleProperty;
import com.hstyle.admin.service.ModulePropertyService;
import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;
import com.hstyle.framework.exception.ApplicationException;
/**
 * 模块配置控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/moduleProperty")
public class ModulePropertyController extends BaseController{
	/*@Autowired
	private RedisTemplate jedisTemplate;*/
	private ModulePropertyService modulePropertyService;
	

	/**
	 * 列出系统级配置
	 * @param code
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<ModuleProperty> list(@RequestParam String code) {
		List<ModuleProperty> modulePros = modulePropertyService.getPropertyByModule(code);
		return modulePros;
	}
	
	/**
	 * 
	 * @param property
	 * @return
	 */
	@RequestMapping("/addModuleProperty")
	@ResponseBody
	public Msg addModuleProperty(@Valid ModuleProperty property){
	
			List<ModuleProperty> modCodePros = modulePropertyService.getPropertyByCode(property.getCode(),property.getModule());
			if (modCodePros.size()>0) {
				return fail("该模块下模块配置代码不能重复!");
			}
			List<ModuleProperty> modNamePros = modulePropertyService.getPropertyByName(property.getName(), property.getModule());
			if (modNamePros.size()>0) {
				return fail("该模块下模块配置名称不能重复!");
			}
			List<ModuleProperty> modOrderPros = modulePropertyService.getPropertyByOrderNo(String.valueOf(property.getOrderNo()), property.getModule());
			if (modOrderPros.size()>0) {
				return fail("该模块下序号不能重复!");
			}
			if(property.getOptions().length()>200){
				return fail("配置项长度过大！");
			}
			property.setBusicode("GLOBAL");//配置标示
			modulePropertyService.save(property);
		
		return success("保存成功!");
	}
	
	/**
	 * 
	 * @param property
	 * @return
	 */
	@RequestMapping("/editModuleProperty")
	@ResponseBody
	public Msg editModuleProperty(@Valid ModuleProperty property) {
		ModuleProperty mp = modulePropertyService.get(ModuleProperty.class,property.getId());
		if (mp==null) {
			return fail("该模块配置不存在！");
		}
		
		List<ModuleProperty> modNamePros = modulePropertyService.getPropertyByName(property.getName(), property.getModule());
		if (modNamePros.size()>1) {
			return fail("该模块下模块配置名称不能重复!");
		}
		List<ModuleProperty> modOrderPros = modulePropertyService.getPropertyByOrderNo(String.valueOf(property.getOrderNo()), property.getModule());
		if (modOrderPros.size()>1) {
			return fail("该模块下序号不能重复!");
		}
		if(property.getOptions().length()>200){
			return fail("配置项长度过大！");
		}
		modulePropertyService.editModuleProperty(property,mp);
		return success("修改成功");
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteModuleProperty")
	@ResponseBody
	public Msg deleteModuleProperty(@RequestParam long id) {
		ModuleProperty mp = modulePropertyService.get(ModuleProperty.class,id);
		if (mp==null) {
			return fail("该条数据不存在！");
		}
		modulePropertyService.deleteModuleProperty(mp);
		return success("删除成功！");
	}
	
	/**
	 * 修改配置
	 * @param properties
	 * @return
	 */
	@RequestMapping("updateConfig")
	@ResponseBody
	public Msg updateConfig(@RequestParam String properties,String busicode,String moduleType){
		try {
			modulePropertyService.updateConfig(properties,busicode,moduleType);
		} catch (ApplicationException e) {
			e.printStackTrace();
			return fail(e.getMessage());
		}
		return success();
		
	}
	@RequestMapping("/listSysConfig")
	@ResponseBody
	public List<ModuleProperty> listSysConfig(HttpServletRequest request){
		String module=request.getParameter("module");
		String busicode=request.getParameter("busicode");
		return modulePropertyService.listSysConfig(module,busicode);
		 
	}
	
	
	public ModulePropertyService getModulePropertyService() {
		return modulePropertyService;
	}
	@Autowired
	public void setModulePropertyService(ModulePropertyService modulePropertyService) {
		this.modulePropertyService = modulePropertyService;
		/*List<ModuleProperty> modulePropertylist=modulePropertyService.findAll();
		if(modulePropertylist==null || modulePropertylist.size()==0)return ;
		try
		{
			ValueOperations<String,String> vops=jedisTemplate.opsForValue();
			for(ModuleProperty property:modulePropertylist){
				String key=property.getModuleType()+":"+property.getBusicode()+":"+property.getCode();
				String value=property.getValue();
				vops.set(key, value);
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
	}
	
	
	
}
