package com.hstyle.framework.dao.core.hibernate.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.EqRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.GeRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.GtRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.InRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.LLikeRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.LeRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.LikeRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.LtRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.NeRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.NinRestriction;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.RLikeRestriction;
import org.hibernate.criterion.Criterion;

/**
 * Hibernate属性过滤器约束持有者，帮助HibernateDao对buildCriterion方法创建相对的Criterion对象给Hibernate查询
 * 
 * @author
 *
 */
public class PropertyFilterRestrictionHolder {
	
	
	private static Map<String, PropertyCriterionBuilder> criterionMap = new HashMap<String, PropertyCriterionBuilder>();
	
	static {
		PropertyCriterionBuilder eqRestriction = new EqRestriction();
		PropertyCriterionBuilder neRestriction = new NeRestriction();
		PropertyCriterionBuilder geRestriction = new GeRestriction();
		PropertyCriterionBuilder gtRestriction = new GtRestriction();
		PropertyCriterionBuilder inRestriction = new InRestriction();
		PropertyCriterionBuilder lLikeRestriction = new LLikeRestriction();
		PropertyCriterionBuilder leRestriction = new LeRestriction();
		PropertyCriterionBuilder likeRestriction = new LikeRestriction();
		PropertyCriterionBuilder ltRestriction = new LtRestriction();
		PropertyCriterionBuilder notInRestriction = new NinRestriction();
		PropertyCriterionBuilder rLikeRestriction = new RLikeRestriction();
		
		criterionMap.put(eqRestriction.getRestrictionName(), eqRestriction);
		criterionMap.put(neRestriction.getRestrictionName(), neRestriction);
		criterionMap.put(geRestriction.getRestrictionName(), geRestriction);
		criterionMap.put(inRestriction.getRestrictionName(), inRestriction);
		criterionMap.put(gtRestriction.getRestrictionName(), gtRestriction);
		criterionMap.put(lLikeRestriction.getRestrictionName(), lLikeRestriction);
		criterionMap.put(leRestriction.getRestrictionName(), leRestriction);
		criterionMap.put(likeRestriction.getRestrictionName(), likeRestriction);
		criterionMap.put(ltRestriction.getRestrictionName(), ltRestriction);
		criterionMap.put(rLikeRestriction.getRestrictionName(), rLikeRestriction);
		criterionMap.put(notInRestriction.getRestrictionName(), notInRestriction);
	}
	
	/**
	 * 通过{@link PropertyFilter} 创建Hibernate约束标准
	 * 
	 * @param filter 属性过滤器
	 * 
	 * @return {@link Criterion}
	 */
	public static Criterion getCriterion(PropertyFilter filter) {
		PropertyCriterionBuilder criterionBuilder = criterionMap.get(filter.getRestrictionName());
		return criterionBuilder.build(filter);
	}
	
	/**
	 * 创建Hibernate约束标准
	 * 
	 * @param propertyName 属性名称
	 * @param value 值
	 * @param restrictionName 约束名称
	 * 
	 * @return {@link Criterion}
	 */
	public static Criterion getCriterion(String propertyName,Object value,String restrictionName) {
		PropertyCriterionBuilder restriction = criterionMap.get(restrictionName);
		return restriction.build(propertyName, value);
	}
	
	/**
	 * 通过表达式和对比值创建条件过滤器集合,要求表达式与值必须相等
	 * <p>
	 * 	如：
	 * </p>
	 * <code>
	 * 	PropertyFilerRestriction.createrPropertyFilter(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"})
	 * </code>
	 * <p>
	 * 	对比值长度与表达式长度必须相等
	 * </p>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * 
	 * @return List
	 */
	public static List<PropertyFilter> createPropertyFilter(String[] expressions,String[] matchValues) {
		if (ArrayUtils.isEmpty(expressions) && ArrayUtils.isEmpty(matchValues)) {
			return Collections.emptyList();
		}
		
		if (expressions.length != matchValues.length) {
			throw new IllegalAccessError("expressions中的值与matchValues不匹配，matchValues的长度为:" + matchValues.length + "而expressions的长度为:" + expressions.length);
		}
		
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		
		for (int i = 0; i < expressions.length; i++) {
			filters.add(createPropertyFilter(expressions[i], matchValues[i]));
		}
		
		return filters;
	}
	
	/**
	 * 通过表达式和对比值创建条件过滤器
	 * <p>
	 * 	如：
	 * </p>
	 * <code>
	 * 	PropertyFilerRestriction.createrPropertyFilter("EQ_S_propertyName","vincent")
	 * </code>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * 
	 * @return {@link PropertyFilter}
	 */
	public static PropertyFilter createPropertyFilter(String expression,String matchValue) {
		
		String restrictionsName = StringUtils.substringBefore(expression, "_");
		
		if (!criterionMap.containsKey(restrictionsName)) {
			throw new IllegalAccessError("[" + expression + "]表达式找不到相应的约束名称,获取的值为:" + restrictionsName);

		}
		String classType = StringUtils.substringBetween(expression, "_");
		PropertyType propertyType = null;
		try {
			propertyType = PropertyType.valueOf(classType);
		} catch (Exception e) {
			throw new IllegalAccessError("[" + expression + "]表达式找不到相应的属性类型,获取的值为:" + classType);
		}
		
		String[] propertyNames = null;
		
		if (StringUtils.contains(expression,"_OR_")) {
			String temp = StringUtils.substringAfter(expression, restrictionsName + "_" + classType + "_");
			propertyNames = StringUtils.splitByWholeSeparator(temp, "_OR_");
		} else {
			propertyNames = new String[1];
			propertyNames[0] = StringUtils.substringAfterLast(expression, "_");
		}
		
		return new PropertyFilter(restrictionsName, propertyType, propertyNames,matchValue);
	}
	
	/**
	 * 获取Criterion的Map
	 * 
	 * @return Map
	 */
	public static Map<String, PropertyCriterionBuilder> getCriterionMap() {
		return criterionMap;
	}
	
	/**
	 * 设置Criterion的Map
	 * 
	 * @return Map
	 */
	public static void setCriterionMap(Map<String, PropertyCriterionBuilder> map) {
		criterionMap.putAll(map);
	}
	
	/**
	 * 从HttpRequest参数中创建PropertyFilter列表, 默认Filter属性名前缀为filter.
	 * 当参数存在{filter_EQ_S_property1:value,filter_EQ_S_property2:''}该形式的时候，将不会创建filter_EQ_S_property2等于""值的实例
	 * 参考{@link PropertyFilterRestrictionHolder#buildPropertyFilter(HttpServletRequest, String, boolean)}
	 * 
	 * @param request HttpServletRequest
	 */
	/*public static List<PropertyFilter> buildFromHttpRequest(HttpServletRequest request) {
		return buildFromHttpRequest(request, "filter");
	}
	
	*//**
	 * 从HttpRequest参数中创建PropertyFilter列表,当参数存在{filter_EQ_S_property1:value,filter_EQ_S_property2:''}
	 * 该形式的时候，将不会创建filter_EQ_S_property2等于""值的实例
	 * 参考{@link PropertyFilterRestrictionHolder#buildPropertyFilter(HttpServletRequest, String, boolean)}
	 * 
	 * @param request HttpServletRequest
	 * @param filterPrefix 用于识别是propertyfilter参数的前准
	 * 
	 * @return List
	 *//*
	public static List<PropertyFilter> buildFromHttpRequest(HttpServletRequest request,String filterPrefix) {
		return buildPropertyFilter(request, "filter",true);
	}
	
	*//**
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
	 *//*
	public static List<PropertyFilter> buildFromHttpRequest(HttpServletRequest request,boolean ignoreEmptyValue) {
		return buildPropertyFilter(request, "filter",ignoreEmptyValue);
	}

	*//**
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
	 *//*
	public static List<PropertyFilter> buildPropertyFilter(HttpServletRequest request,String filterPrefix,boolean ignoreEmptyValue) {

		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, Object> filterParamMap = ServletUtils.getParametersStartingWith(request, filterPrefix + "_");

		return buildPropertyFilter(filterParamMap,ignoreEmptyValue);
	}*/
	
	/**
	 * 从Map中创建PropertyFilter列表，如:
	 * 
	 * <pre>
     * Map o = new HashMap();
	 * o.put("EQ_S_property1","value");
	 * o.put("EQ_S_property2","");
	 * List filters = buildPropertyFilter(o,false);
	 * 当前filters:EQ_S_proerpty1="value",EQ_S_proerpty1=""
	 * 
	 * Map o = new HashMap();
	 * o.put("EQ_S_property1","value");
	 * o.put("EQ_S_property2","");
	 * List filters = buildPropertyFilter(o,true);
	 * 当前filters:EQ_S_proerpty1="value"
     * </pre>
	 * 
	 * 
	 * @param filters 过滤器信息
	 * @param ignoreEmptyValue true表示当存在 null或者""值时忽略该PropertyFilter
	 * 
	 */
	public static List<PropertyFilter> buildPropertyFilter(Map<String, Object> filters,boolean ignoreEmptyValue) {
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>();
		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, Object> entry : filters.entrySet()) {
			String expression = entry.getKey();
			Object value = entry.getValue();
			//如果ignoreEmptyValue为true忽略null或""的值
			if (ignoreEmptyValue && (value == null || value.toString().equals(""))) {
				continue;
			}
			//如果ignoreEmptyValue为true忽略null或""的值
			PropertyFilter filter = createPropertyFilter(expression, value.toString());
			filterList.add(filter);
			
		}
		return filterList;
	}
}
