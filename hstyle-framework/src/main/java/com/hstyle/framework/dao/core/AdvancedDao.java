package com.hstyle.framework.dao.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.hstyle.framework.dao.core.hibernate.property.PropertyCriterionBuilder;

public interface AdvancedDao<T, PK extends Serializable> extends BasicDao<T, PK>{
	/**
	 * 保存前判断实体一个属性或几个属性值是否唯一
	 * @param entity
	 * @return true or false
	 */
	boolean isUnique(T entity);

	/**
	 * 根据{@link PropertyFilter} 查询全部
	 * 
	 * @param filters 属性过滤器
	 * 
	 * @return List
	 */
	List<T> findByPropertyFilters(List<PropertyFilter> filters);

	/**
	 * 根据{@link PropertyFilter} 查询全部
	 * 
	 * @param filters 属性过滤器
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * 
	 * @return List
	 */
	List<T> findByPropertyFilters(List<PropertyFilter> filters, String orderBy);

	/**
	 * 根据{@link PropertyFilter} 查询全部
	 * 
	 * @param filters 属性过滤器
	 * @param persistentClass orm实体Class
	 * 
	 * @return List
	 */
	<X> List<X> findByPropertyFilters(List<PropertyFilter> filters,
			Class<?> persistentClass);

	/**
	 * 根据{@link PropertyFilter} 查询全部
	 * 
	 * @param filters 属性过滤器
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param persistentClass orm实体Class
	 * 
	 * @return List
	 */
	<X> List<X> findByPropertyFilters(List<PropertyFilter> filters,
			String orderBy, Class<?> persistentClass);

	/**
	 * 通过表达式和对比值查询全部
	 * <pre>
	 * 	如：
	 * 	findByExpressions(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"})
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * 
	 * @return List
	 */
	List<T> findByExpressions(String[] expressions, String[] matchValues);

	/**
	 * 通过表达式和对比值查询全部
	 * <pre>
	 * 	如：
	 * 	findByExpressions(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"},"propertyName_asc")
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * 
	 * @return List
	 */
	List<T> findByExpressions(String[] expressions, String[] matchValues,
			String orderBy);

	/**
	 * 通过表达式和对比值查询全部
	 * <pre>
	 * 	如：
	 * 	findByExpressions(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"},OtherOrm.class)
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param persistentClass orm实体Class
	 * 
	 * @return List
	 */
	<X> List<X> findByExpressions(String[] expressions, String[] matchValues,
			Class<?> persistentClass);

	/**
	 * 通过表达式和对比值查询全部
	 * <pre>
	 * 	如：
	 * 	findByExpressions(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"},"propertyName_asc",OtherOrm.class)
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param persistentClass orm实体Class
	 * 
	 * @return List
	 */
	<X> List<X> findByExpressions(String[] expressions, String[] matchValues,
			String orderBy, Class<?> persistentClass);

	/**
	 * 通过表达式和对比值查询全部
	 * <pre>
	 * 	如：
	 * 	findByExpression("EQ_S_propertyName","vincent")
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param persistentClass orm实体Class
	 * 
	 * @return List
	 */
	List<T> findByExpression(String expression, String matchValue);

	/**
	 * 通过表达式和对比值查询全部
	 * <pre>
	 * 	如：
	 * 	findByExpression("EQ_S_propertyName","vincent","propertyName_asc")
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * 
	 * @return List
	 */
	List<T> findByExpression(String expression, String matchValue,
			String orderBy);

	/**
	 * 通过表达式和对比值查询全部
	 * 
	 * <pre>
	 * 	如：
	 * 	findByExpression("EQ_S_propertyName","vincent",OtherOrm.class)
	 * </p>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param persistentClass orm实体Class
	 * 
	 * @return List
	 */
	<X> List<X> findByExpression(String expression, String matchValue,
			Class<?> persistentClass);

	/**
	 * 通过表达式和对比值查询全部
	 * <pre>
	 * 	如：
	 * 	findByExpression("EQ_S_propertyName","vincent",OtherOrm.class)
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param persistentClass orm实体Class
	 * 
	 * @return List
	 */
	<X> List<X> findByExpression(String expression, String matchValue,
			String orderBy, Class<?> persistentClass);

	/**
	 * 通过orm实体属性名称查询全部
	 * 
	 * @param propertyName orm实体属性名称
	 * @param value 值
	 * 
	 * @return List
	 */
	List<T> findByProperty(String propertyName, Object value);

	/**
	 * 通过orm实体属性名称查询全部
	 * 
	 * @param propertyName orm实体属性名称
	 * @param value 值
	 * @param restrictionName 约束名称,参考{@link PropertyCriterionBuilder}的实现类
	 * 
	 * @return List
	 */
	List<T> findByProperty(String propertyName, Object value,
			String restrictionName);

	/**
	 * 通过orm实体属性名称查询全部
	 * 
	 * @param propertyName orm实体属性名称
	 * @param value 值
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * 
	 * @return List
	 */
	List<T> findByPropertyWithOrderBy(String propertyName, Object value,
			String orderBy);

	/**
	 * 通过orm实体属性名称查询全部
	 * 
	 * @param propertyName orm实体属性名称
	 * @param value 值
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param restrictionName 约束名称,参考{@link PropertyCriterionBuilder}的实现类
	 * 
	 * @return List
	 */
	List<T> findByPropertyWithOrderBy(String propertyName, Object value,
			String orderBy, String restrictionName);

	/**
	 * 通过orm实体属性名称查询全部
	 * 
	 * @param propertyName orm实体属性名称
	 * @param value 值
	 * @param persistentClass orm实体Class
	 * 
	 * @return List
	 */
	<X> List<X> findByProperty(String propertyName, Object value,
			Class<?> persistentClass);

	/**
	 * 通过orm实体属性名称查询全部
	 * 
	 * @param propertyName orm实体属性名称
	 * @param value 值
	 * @param persistentClass orm实体Class
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * 
	 * @return List
	 */
	<X> List<X> findByPropertyWithOrderBy(String propertyName, Object value,
			Class<?> persistentClass, String orderBy);

	/**
	 * 通过orm实体属性名称查询全部
	 * 
	 * @param propertyName orm实体属性名称
	 * @param value 值
	 * @param restrictionName 约束名称,参考{@link PropertyCriterionBuilder}的实现类
	 * @param persistentClass orm实体Class
	 * 
	 * @return List
	 */
	<X> List<X> findByProperty(String propertyName, Object value,
			String restrictionName, Class<?> persistentClass);

	/**
	 * 通过orm实体属性名称查询全部
	 * 
	 * @param propertyName orm实体属性名称
	 * @param value 值
	 * @param restrictionName 约束名称,参考{@link PropertyCriterionBuilder}的实现类
	 * @param persistentClass orm实体Class
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * 
	 * @return List
	 */
	<X> List<X> findByProperty(String propertyName, Object value,
			String restrictionName, Class<?> persistentClass, String orderBy);

	/**
	 * 通过{@link PropertyFilter} 查询单个orm实体
	 * 
	 * @param filters 条件过滤器
	 * 
	 * @return Object
	 * 
	 */
	T findUniqueByPropertyFilters(List<PropertyFilter> filters);

	/**
	 * 通过{@link PropertyFilter} 查询单个orm实体
	 * 
	 * @param filters 条件过滤器
	 * @param persistentClass orm 实体Class
	 * 
	 * @return Object
	 * 
	 */
	<X> X findUniqueByPropertyFilters(List<PropertyFilter> filters,
			Class<?> persistentClass);

	/**
	 * 通过表达式和对比值查询单个orm实体
	 * <pre>
	 * 	如：
	 * 	findUniqueByExpression("EQ_S_propertyName","vincent")
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * 
	 * @return Object
	 */
	T findUniqueByExpression(String expression, String matchValue);

	/**
	 * 通过表达式和对比值查询单个orm实体
	 * <pre>
	 * 	如：
	 * 	findUniqueByExpressions(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"})
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * 
	 * @return Object
	 */
	T findUniqueByExpressions(String[] expressions, String[] matchValues);

	/**
	 * 通过criterion数组查询单个orm实体
	 * 
	 * @param criterions criterion数组
	 * @param persistentClass orm实体Class
	 * 
	 * @return Object
	 */
	<X> X findUniqueByCriterions(Criterion[] criterions,
			Class<?> persistentClass);

	/**
	 * 通过表达式和对比值查询单个orm实体
	 * <pre>
	 * 	如：
	 * 	findUniqueByExpression("EQ_S_propertyName","vincent",OtherOrm.class)
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * 
	 * @return Object
	 */
	<X> X findUniqueByExpression(String expression, String matchValue,
			Class<?> persistentClass);

	/**
	 * 通过表达式和对比值查询单个orm实体
	 * <pre>
	 * 	如：
	 * 	findUniqueByExpressions(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"},OtherOrm.class)
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param persistentClass orm实体Class
	 * 
	 * @return Object
	 */
	<X> X findUniqueByExpressions(String[] expressions, String[] matchValues,
			Class<?> persistentClass);

	/**
	 * 通过orm实体的属性名称查询单个orm实体
	 * 
	 * @param propertyName 属性名称
	 * @param value 值
	 * 
	 * @return Object
	 */
	T findUniqueByProperty(String propertyName, Object value);

	/**
	 * 通过orm实体的属性名称查询单个orm实体
	 * 
	 * @param propertyName 属性名称
	 * @param value 值
	 * @param restrictionName 约束名称 参考{@link PropertyCriterionBuilder}的所有实现类
	 * 
	 * @return Object
	 */
	T findUniqueByProperty(String propertyName, Object value,
			String restrictionName);

	/**
	 * 通过orm实体的属性名称查询单个orm实体
	 * 
	 * @param propertyName 属性名称
	 * @param value 值
	 * @param persistentClass ORM对象类型Class
	 * 
	 * @return Object
	 */
	<X> X findUniqueByProperty(String propertyName, Object value,
			Class<?> persistentClass);

	/**
	 * 通过orm实体的属性名称查询单个orm实体
	 * 
	 * @param propertyName 属性名称
	 * @param value 值
	 * @param restrictionName 约束名称 参考{@link PropertyCriterionBuilder}的所有实现类
	 * @param persistentClass orm实体Class
	 * 
	 * @return Object
	 */
	<X> X findUniqueByProperty(String propertyName, Object value,
			String restrictionName, Class<?> persistentClass);

	/**
	 * 通过分页参数查询所有
	 * @param request 分页参数
	 * @return
	 */
	Page<T> findPage(PageRequest request);

	/**
	 * 通过分页参数，和条件过滤器查询分页
	 * 
	 * @param request
	 * @param filters
	 * 
	 * @return {@link Page}
	 */
	Page<T> findPage(PageRequest request, List<PropertyFilter> filters);

	/**
	 * 通过{@link PropertyFilter}和分页请求参数获取分页对象
	 * 
	 * @param request 分页请求参数
	 * @param filters 属性过滤器集合
	 * @param persistentClass orm实体Class
	 * 
	 * 
	 * @return {@link Page}
	 */
	<X> Page<X> findPage(PageRequest request, List<PropertyFilter> filters,
			Class<?> persistentClass);
	/**
	 * 
	* @Title: findFilters
	* @Description: TODO(PropertyFilter),查询符合条件的所有数据
	* @param @param filters
	* @param @param persistentClass
	* @param @return    设定文件
	* @return List<T>    返回类型
	* @throws
	 */
	public List<T> findFilters(List<PropertyFilter> filters,Class<?> persistentClass);
	/**
	 * 通过分页请求参数和表达式与对比值获取分页对象
	 * 
	 *  <pre>
	 * 	如：
	 * 	findPage(request,"EQ_S_propertyName","vincent")
	 * </pre>
	 * 
	 * @param request 分页请求参数
	 * @param expression 表达式
	 * @param matchValue 对比值
	 * 
	 * @return {@link Page}
	 */
	Page<T> findPage(PageRequest request, String expression, String matchValue);

	/**
	 * 通过分页请求参数和表达式与对比值获取分页对象
	 *  <pre>
	 * 	如：
	 * 	findPage(request,"EQ_S_propertyName","vincent",OtherOrm.class)
	 * </pre>
	 * 
	 * @param request 分页请求参数
	 * @param expression 表达式
	 * @param matchValue 对比值
	 * @param persistentClass orm实体Class
	 * 
	 * @return {@link Page}
	 */
	<X> Page<X> findPage(PageRequest request, String expression,
			String matchValue, Class<?> persistentClass);

	/**
	 * 通过表达式和对比值获取分页对象
	 * <pre>
	 * 	如：
	 * 	findPage(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"})
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * 
	 * @return Object
	 */
	Page<T> findPage(PageRequest request, String[] expressions,
			String[] matchValues);

	/**
	 * 通过表达式和对比值获取分页对象
	 * <pre>
	 * 	如：
	 * 	findPage(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"},OtherOrm.class)
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param persistentClass orm实体Class
	 * 
	 * @return Object
	 */
	<X> Page<X> findPage(PageRequest request, String[] expressions,
			String[] matchValues, Class<?> persistentClass);

	/**
	 * 通过分页参数与HQL语句获取分页对象
	 * 
	 * @param request 分页请求参数
	 * @param queryString HQL语句
	 * @param values 值
	 * 
	 * @return {@link Page}
	 */
	<X> Page<X> findPageByQuery(PageRequest request, String queryString,
			Object... values);

	/**
	 * 通过分页参数与HQL语句获取分页对象
	 * 
	 * @param request 分页请求参数
	 * @param queryString HQL语句
	 * @param values 值
	 * 
	 * @return {@link Page}
	 */
	<X> Page<X> findPageByQuery(PageRequest request, String queryString,
			Map<String, Object> values);
	
	/**
	 *  分页查询，根据业务实体的属性值and查询
	 * @param entity
	 * @param pageRequest 分页参数
	 * @return
	 */
	Page<T> findByEntityPaged(T entity,PageRequest request );
	
	/**
	 *  分页查询，根据业务实体的属性值and查询
	 * @param entity
	 * @param pageRequest 分页参数
	 * @param isLike 是否启用模糊查询  true：启用  false：停用
	 * @return
	 */
	Page<T> findByEntityPaged(T entity,PageRequest request,boolean isLike );

}