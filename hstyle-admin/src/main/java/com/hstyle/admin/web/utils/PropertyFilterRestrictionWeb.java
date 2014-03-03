/**
 * 
 */
package com.hstyle.admin.web.utils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.dao.core.hibernate.property.PropertyFilterRestrictionHolder;

/**
 * @author Administrator
 *
 */
public class PropertyFilterRestrictionWeb extends	PropertyFilterRestrictionHolder {
	
	/**
	 * 从HttpRequest参数中创建PropertyFilter列表, 默认Filter属性名前缀为filter.
	 * 当参数存在{filter_EQ_S_property1:value,filter_EQ_S_property2:''}该形式的时候，将不会创建filter_EQ_S_property2等于""值的实例
	 * 参考{@link PropertyFilterRestrictionHolder#buildPropertyFilter(HttpServletRequest, String, boolean)}
	 * 
	 * @param request HttpServletRequest
	 */
	public static List<PropertyFilter> buildFromHttpRequest(HttpServletRequest request) {
		return buildFromHttpRequest(request, "filter");
	}
	
	/**
	 * 从HttpRequest参数中创建PropertyFilter列表,当参数存在{filter_EQ_S_property1:value,filter_EQ_S_property2:''}
	 * 该形式的时候，将不会创建filter_EQ_S_property2等于""值的实例
	 * 参考{@link PropertyFilterRestrictionHolder#buildPropertyFilter(HttpServletRequest, String, boolean)}
	 * 
	 * @param request HttpServletRequest
	 * @param filterPrefix 用于识别是propertyfilter参数的前准
	 * 
	 * @return List
	 */
	public static List<PropertyFilter> buildFromHttpRequest(HttpServletRequest request,String filterPrefix) {
		return buildPropertyFilter(request, filterPrefix,true);
	}
	
	/**
	 * 从HttpRequest参数中创建PropertyFilter列表,当参数存在{filter_EQ_S_property1:value,filter_EQ_S_property2:''}
	 * 该形式的时候，将不会创建filter_EQ_S_property2等于""值的实例
	 * 参考{@link PropertyFilterRestrictionHolder#buildPropertyFilter(HttpServletRequest, String, boolean)}
	 * 
	 * <pre>
	 * 当页面提交的参数为:{filter_EQ_S_property1:value,filter_EQ_S_property2:''}
	 * List filters =buildPropertyFilter(request,"filter",false);
	 * 当前filters:EQ_S_proerpty1="value",EQ_S_proerpty1=""
	 * 
	 * 当页面提交的参数为:{filter_EQ_S_property1:value,filter_EQ_S_property2:''}
	 * List filters =buildPropertyFilter(request,"filter",true);
	 * 当前filters:EQ_S_proerpty1="value"
	 * </pre>
	 * 
	 * @param request HttpServletRequest
	 * @param ignoreEmptyValue true表示当存在""值时忽略该PropertyFilter
	 * 
	 * @return List
	 */
	public static List<PropertyFilter> buildFromHttpRequest(HttpServletRequest request,boolean ignoreEmptyValue) {
		return buildPropertyFilter(request, "filter",ignoreEmptyValue);
	}

	/**
	 * 从HttpRequest参数中创建PropertyFilter列表,例子:
	 * 
	 * <pre>
	 * 当页面提交的参数为:{filter_EQ_S_property1:value,filter_EQ_S_property2:''}
	 * List filters =buildPropertyFilter(request,"filter",false);
	 * 当前filters:EQ_S_proerpty1="value",EQ_S_proerpty1=""
	 * 
	 * 当页面提交的参数为:{filter_EQ_S_property1:value,filter_EQ_S_property2:''}
	 * List filters =buildPropertyFilter(request,"filter",true);
	 * 当前filters:EQ_S_proerpty1="value"
	 * </pre>
	 * 
	 * @param request HttpServletRequest
	 * @param filterPrefix 用于识别是propertyfilter参数的前准
	 * @param ignoreEmptyValue true表示当存在""值时忽略该PropertyFilter
	 * 
	 * @return List
	 */
	public static List<PropertyFilter> buildPropertyFilter(HttpServletRequest request,String filterPrefix,boolean ignoreEmptyValue) {

		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request, filterPrefix + "_");

		return buildPropertyFilter(filterParamMap,ignoreEmptyValue);
	}

}
