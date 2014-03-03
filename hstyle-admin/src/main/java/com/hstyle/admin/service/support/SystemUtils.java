package com.hstyle.admin.service.support;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.permission.DomainPermission;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hstyle.admin.common.DictionaryCode;
import com.hstyle.admin.common.UserContext;
import com.hstyle.admin.model.Dictionary;
import com.hstyle.admin.service.DictionaryService;
import com.hstyle.framework.utils.SpringContextUtil;

/**
 * 系统辅助工具类
 * @author Administrator
 *
 */
@Component
public class SystemUtils {
	public static  String DefaultDictionaryValue = "无";
	
	private static DictionaryService dictionaryService;

	/**
	 * 获取数据字典名称
	 * 
	 * @param dictionaryCode 列别代码
	 * @param value 值
	 * 
	 * @return String
	 */
	public static String getDictionaryNameByValue(DictionaryCode dictionaryCode,String value) {
		
		if (value == null || dictionaryCode == null) {
			return DefaultDictionaryValue;
		}
		
		if (value instanceof String && StringUtils.isEmpty(value.toString())) {
			return DefaultDictionaryValue;
		}
		if(dictionaryService==null)
			dictionaryService=(DictionaryService) SpringContextUtil.getBean("dictionaryService");
		List<Dictionary> dictionaries = dictionaryService.getDictionariesByCode(dictionaryCode.getCode());
		
		for (Iterator<Dictionary> iterator = dictionaries.iterator(); iterator.hasNext();) {
			Dictionary dictionary = iterator.next();
			
			if (dictionary.getValue().equals(value)) {
				return dictionary.getName();
			}
		}
		return DefaultDictionaryValue; 
	}

		
	/**
	 * 通过字典类别代码获取数据字典集合
	 * 
	 * @param code 字典类别
	 * 
	 * @return List
	 */
	public  static List<Dictionary> getDataDictionariesByCategoryCode(DictionaryCode dictionaryCode) {
		if(dictionaryService==null)
			dictionaryService=(DictionaryService) SpringContextUtil.getBean("dictionaryService");
		return dictionaryService.getDictionariesByCode(dictionaryCode.getCode());
	}
	/**
	 * 获取当前安全模型
	 * 
	 * @return {@link SecurityModel}
	 */
	public static UserContext getUserContext() {
		
		Subject subject = SecurityUtils.getSubject();
		UserContext userContext=null;
		if (subject != null && subject.getPrincipal() != null && subject.getPrincipal() instanceof UserContext) {
			userContext= (UserContext) subject.getPrincipal();
		}
		
		return userContext;
	}
	
	/**
	 * 判断当前会话是否登录
	 * 
	 * @return boolean
	 */
	public static boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}
	
	
	/**
	 * 判断当前权限表达式是否已授权
	 * @param permissionExpression
	 * @return
	 */
	public static boolean isDataPermitted(String permissionExpression){
		return SecurityUtils.getSubject().isPermitted("DP"+permissionExpression);
	}
	
	/**
	 * 根据权限表达式，获得当前用户的已授权操作的数据
	 * @param permissionExpression
	 * @return
	 */
	public static Set<String> getTargets(String permissionExpression){
		Set<String> targetSet=null;
        Subject subject = SecurityUtils.getSubject();
        UserContext userContext=null;
		if (subject != null && subject.getPrincipal() != null && subject.getPrincipal() instanceof UserContext) {
			userContext= (UserContext) subject.getPrincipal();
		}
		if(userContext!=null){
			DomainPermission domainPermission=userContext.getDomainPermission(permissionExpression);
			if(domainPermission!=null)
				targetSet= domainPermission.getTargets();
		}
		return targetSet;
	}

	@Autowired
	public void setDictionaryService(DictionaryService dictionaryService) {
		SystemUtils.dictionaryService = dictionaryService;
	}
	/**
	 * 为了能够借助Spring自动注入dictionaryService这个Bean.写一个空方法借助@PostConstruct注解注入
	 */
	@PostConstruct
	public void init() {
		
	}
}
