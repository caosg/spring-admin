package com.hstyle.framework.dao.core.hibernate;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import com.hstyle.framework.dao.core.AdvancedDao;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PageRequest.Sort;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.dao.core.hibernate.property.PropertyFilterRestrictionHolder;
import com.hstyle.framework.dao.core.hibernate.property.impl.restriction.EqRestriction;
import com.hstyle.framework.utils.CollectionUtils;
import com.hstyle.framework.utils.ReflectionUtils;

/**
 * Hibernate基础扩展类,主要是分页查询，高级条件查询扩展。包含对{@link PropertyFilter}的支持。或其他查询的支持
 * 
 * @author
 *
 * @param <T> 对象
 * @param <PK> 主键Id类型
 */
public class AdvancedHibernateDao<T,PK extends Serializable> extends BasicHibernateDao<T, PK> implements AdvancedDao<T, PK>{

	public AdvancedHibernateDao(){
		super();
	}
	
	public AdvancedHibernateDao(Class<T> entityClass){
		super(entityClass);
	}
	public AdvancedHibernateDao(Class<T> entityClass,SessionFactory sessionFactory){
		super(entityClass, sessionFactory);
		
	}
	
	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 * 
	 * @param c Criteria对象
	 * 
	 * @return long
	 */
	protected long countCriteriaResult( Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl,"orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 执行Count查询
		Long totalCountObject = (Long) c.setProjection(Projections.rowCount()).uniqueResult();
		long totalCount = (totalCountObject != null) ? totalCountObject : 0;

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return totalCount;
	}
	
	/**
	 * 通过表达式和对比值创建Criteria,要求表达式与值必须相等
	 * <pre>
	 * 	如：
	 * 	createCriteria(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"})
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(String[] expressions,String[] matchValues) {
		return createCriteria(expressions, matchValues, StringUtils.EMPTY);
	}
	
	/**
	 * 通过表达式和对比值创建Criteria,要求表达式与值必须相等
	 * <pre>
	 * 	如：
	 * 	createCriteria(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"})
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(String[] expressions,String[] matchValues,String orderBy) {
		return createCriteria(expressions, matchValues, orderBy,this.entityClass);
	}
	
	/**
	 * 通过表达式和对比值创建Criteria,要求表达式与值必须相等
	 * <pre>
	 * 	如：
	 * 	createCriteria(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"})
	 * 	对比值长度与表达式长度必须相等
	 * <pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param persistentClass orm实体Class
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(String[] expressions,String[] matchValues,Class<?> persistentClass) {
		return createCriteria(expressions,matchValues,StringUtils.EMPTY,persistentClass);
	}
	
	/**
	 * 通过表达式和对比值创建Criteria,要求表达式与值必须相等
	 * <pre>
	 * 	如：
	 * 	createCriteria(new String[]{"EQ_S_propertyName1","NE_I_propertyName2"},new String[]{"vincent","vincent_OR_admin"})
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param persistentClass orm实体Class
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(String[] expressions,String[] matchValues,String orderBy,Class<?> persistentClass) {
		List<PropertyFilter> filters = PropertyFilterRestrictionHolder.createPropertyFilter(expressions, matchValues);
		return createCriteria(filters,orderBy,persistentClass);
	}
	
	/**
	 * 根据{@link PropertyFilter}创建Criteria
	 * 
	 * @param filters 属性过滤器
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(List<PropertyFilter> filters) {
		return createCriteria(filters,StringUtils.EMPTY);
	}
	
	/**
	 * 根据{@link PropertyFilter}创建Criteria
	 * 
	 * @param filters 属性过滤器
	 * @param persistentClass orm实体Class
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(List<PropertyFilter> filters,Class<?> persistentClass) {
		return createCriteria(filters,StringUtils.EMPTY,persistentClass);
	}
	
	/**
	 * 根据{@link PropertyFilter}创建Criteria
	 * 
	 * @param filters 属性过滤器
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(List<PropertyFilter> filters,String orderBy) {
		return createCriteria(filters,orderBy, this.entityClass);
	}
	
	/**
	 * 根据{@link PropertyFilter}创建Criteria
	 * 
	 * @param filters 属性过滤器
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param persistentClass orm实体Class
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(List<PropertyFilter> filters,String orderBy,Class<?> persistentClass) {
		
		if(persistentClass == null) {
			persistentClass = this.entityClass;
		}
		
		Criteria criteria = createCriteria(persistentClass,orderBy);
		
		if (CollectionUtils.isEmpty(filters)) {
			return criteria;
		}
		for (PropertyFilter filter : filters) {
			criteria.add(createCriterion(filter));
		}
		return criteria;
	}
	
	/**
	 * 通过表达式和对比值创建Criterion
	 * <pre>
	 * 	如：
	 * 	createCriterion("EQ_S_propertyName","vincent")
	 * 	对比值长度与表达式长度必须相等
	 * </pre>
	 * 
	 * @param expressions 表达式
	 * @param matchValues 对比值
	 * 
	 * @return {@link Criterion}
	 */
	protected Criterion createCriterion(String expression,String matchValue) {
		PropertyFilter filter = PropertyFilterRestrictionHolder.createPropertyFilter(expression, matchValue);
		return createCriterion(filter);
	}
	
	/**
	 * 通过{@link PropertyFilter} 创建 Criterion
	 * 
	 * @param filter 属性过滤器
	 * 
	 * @return {@link Criterion}
	 */
	protected Criterion createCriterion(PropertyFilter filter) {
		if (filter == null) {
			return null;
		}
		return PropertyFilterRestrictionHolder.getCriterion(filter);
	}
	
	/**
	 * 根据detachedCriteria查询全部
	 * 
	 * @param detachedCriteria detachedCriteria
	 * 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	protected <X> List<X> findByDetachedCriteria(DetachedCriteria detachedCriteria) {
		return createCriteria(detachedCriteria).list();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByPropertyFilters(java.util.List)
	 */
	public List<T> findByPropertyFilters(List<PropertyFilter> filters) {
		return findByPropertyFilters(filters,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByPropertyFilters(java.util.List, java.lang.String)
	 */
	public List<T> findByPropertyFilters(List<PropertyFilter> filters,String orderBy) {
		return findByPropertyFilters(filters,orderBy,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByPropertyFilters(java.util.List, java.lang.Class)
	 */
	public <X> List<X> findByPropertyFilters(List<PropertyFilter> filters,Class<?> persistentClass) {
		return findByPropertyFilters(filters,StringUtils.EMPTY,persistentClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByPropertyFilters(java.util.List, java.lang.String, java.lang.Class)
	 */
	public <X> List<X> findByPropertyFilters(List<PropertyFilter> filters,String orderBy,Class<?> persistentClass) {
		return createCriteria(filters, orderBy,persistentClass).list();
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByExpressions(java.lang.String[], java.lang.String[])
	 */
	public List<T> findByExpressions(String[] expressions,String[] matchValues) {
		return findByExpressions(expressions,matchValues,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByExpressions(java.lang.String[], java.lang.String[], java.lang.String)
	 */
	public List<T> findByExpressions(String[] expressions,String[] matchValues,String orderBy) {
		return findByExpressions(expressions,matchValues,orderBy,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByExpressions(java.lang.String[], java.lang.String[], java.lang.Class)
	 */
	public <X> List<X> findByExpressions(String[] expressions,String[] matchValues,Class<?> persistentClass) {
		return findByExpressions(expressions,matchValues,StringUtils.EMPTY,persistentClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByExpressions(java.lang.String[], java.lang.String[], java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByExpressions(String[] expressions,String[] matchValues,String orderBy,Class<?> persistentClass) {
		return createCriteria(expressions, matchValues, orderBy, persistentClass).list();
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByExpression(java.lang.String, java.lang.String)
	 */
	public List<T> findByExpression(String expression,String matchValue) {
		return findByExpression(expression,matchValue,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByExpression(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<T> findByExpression(String expression,String matchValue,String orderBy) {
		return findByExpression(expression,matchValue,orderBy,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByExpression(java.lang.String, java.lang.String, java.lang.Class)
	 */
	public <X> List<X> findByExpression(String expression,String matchValue,Class<?> persistentClass) {
		return findByExpression(expression,matchValue,StringUtils.EMPTY,persistentClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByExpression(java.lang.String, java.lang.String, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByExpression(String expression,String matchValue,String orderBy,Class<?> persistentClass) {
		return createCriteria(persistentClass, orderBy, createCriterion(expression, matchValue)).list();
	}
	

	/**
	 * 根据Criterion查询全部
	 * 
	 * @param criterions 可变长度的Criterion数组
	 * 
	 * @return List
	 */
	protected List<T> findByCriterion(Criterion...criterions) {
		return findByCriterion(this.entityClass,criterions);
	}

	/**
	 * 根据Criterion查询全部
	 * 
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param criterions 可变长度的Criterion数组
	 * 
	 * @return List
	 */
	protected List<T> findByCriterion(String orderBy,Criterion...criterions) {
		return findByCriterion(this.entityClass,orderBy,criterions);
	}
	
	/**
	 * 根据Criterion查询全部
	 * 
	 * @param persistentClass orm实体Class
	 * @param criterions 可变长度的Criterion数组
	 * 
	 * @return List
	 */
	protected <X> List<X> findByCriterion(Class<?> persistentClass,Criterion...criterions) {
		return findByCriterion(persistentClass,StringUtils.EMPTY,criterions);
	}
	
	/**
	 * 根据Criterion查询全部
	 * 
	 * @param persistentClass orm实体Class
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param criterions 可变长度的Criterion数组
	 * 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	protected <X> List<X> findByCriterion(Class<?> persistentClass,String orderBy,Criterion...criterions) {
		return createCriteria(persistentClass, orderBy, criterions).list();
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List<T> findByProperty(String propertyName,Object value) {
		return findByProperty(propertyName, value, EqRestriction.RestrictionName);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByProperty(java.lang.String, java.lang.Object, java.lang.String)
	 */
	public List<T> findByProperty(String propertyName,Object value,String restrictionName) {
		return findByPropertyWithOrderBy(propertyName, value, StringUtils.EMPTY,restrictionName);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByPropertyWithOrderBy(java.lang.String, java.lang.Object, java.lang.String)
	 */
	public List<T> findByPropertyWithOrderBy(String propertyName,Object value,String orderBy) {
		return findByPropertyWithOrderBy(propertyName, value, orderBy, EqRestriction.RestrictionName);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByPropertyWithOrderBy(java.lang.String, java.lang.Object, java.lang.String, java.lang.String)
	 */
	public List<T> findByPropertyWithOrderBy(String propertyName,Object value,String orderBy,String restrictionName) {
		return findByProperty(propertyName, value, restrictionName, this.entityClass,orderBy);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByProperty(java.lang.String, java.lang.Object, java.lang.Class)
	 */
	public <X> List<X> findByProperty(String propertyName,Object value,Class<?> persistentClass) {
		return findByProperty(propertyName, value, EqRestriction.RestrictionName,persistentClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByPropertyWithOrderBy(java.lang.String, java.lang.Object, java.lang.Class, java.lang.String)
	 */
	public <X> List<X> findByPropertyWithOrderBy(String propertyName,Object value,Class<?> persistentClass,String orderBy) {
		return findByProperty(propertyName, value, EqRestriction.RestrictionName,persistentClass,orderBy);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByProperty(java.lang.String, java.lang.Object, java.lang.String, java.lang.Class)
	 */
	public <X> List<X> findByProperty(String propertyName,Object value,String restrictionName,Class<?> persistentClass) {
		return findByProperty(propertyName, value, restrictionName, persistentClass, StringUtils.EMPTY);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findByProperty(java.lang.String, java.lang.Object, java.lang.String, java.lang.Class, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByProperty(String propertyName,Object value,String restrictionName,Class<?> persistentClass,String orderBy) {
		Criterion criterion = PropertyFilterRestrictionHolder.getCriterion(propertyName, value, restrictionName);
		return createCriteria(persistentClass, orderBy, criterion).list();
	}
	
	/**
	 * 通过detachedCriteria查询单个orm实体
	 * 
	 * @param detachedCriteria hibernate detachedCriteria
	 * 
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	protected <X> X findUniqueByDetachedCriteria(DetachedCriteria detachedCriteria) {
		return (X) createCriteria(detachedCriteria).uniqueResult();
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByPropertyFilters(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByPropertyFilters(List<PropertyFilter> filters) {
		return (T)findUniqueByPropertyFilters(filters, this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByPropertyFilters(java.util.List, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByPropertyFilters(List<PropertyFilter> filters,Class<?> persistentClass) {
		return (X) createCriteria(filters, persistentClass).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByExpression(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByExpression(String expression,String matchValue) {
		return (T)findUniqueByExpression(expression, matchValue,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByExpressions(java.lang.String[], java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByExpressions(String[] expressions,String[] matchValues) {
		return (T)findUniqueByExpressions(expressions,matchValues,this.entityClass);
	}
	
	/**
	 * 通过criterion数组查询单个orm实体
	 * 
	 * @param criterions criterion数组
	 * 
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	protected T findUniqueByCriterions(Criterion[] criterions){
		return (T)findUniqueByCriterions(criterions,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByCriterions(org.hibernate.criterion.Criterion[], java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByCriterions(Criterion[] criterions,Class<?> persistentClass){
		return (X)createCriteria(persistentClass,criterions).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByExpression(java.lang.String, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByExpression(String expression,String matchValue,Class<?> persistentClass) {
		Criterion criterion = createCriterion(expression, matchValue);
		return (X)findUniqueByCriterions(new Criterion[]{criterion}, persistentClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByExpressions(java.lang.String[], java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByExpressions(String[] expressions,String[] matchValues,Class<?> persistentClass) {
		return (X)createCriteria(expressions, matchValues, StringUtils.EMPTY, persistentClass).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByProperty(java.lang.String, java.lang.Object)
	 */
	public T findUniqueByProperty(String propertyName,Object value) {
		return (T)findUniqueByProperty(propertyName,value,EqRestriction.RestrictionName);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByProperty(java.lang.String, java.lang.Object, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(String propertyName,Object value,String restrictionName) {
		return (T)findUniqueByProperty(propertyName,value,restrictionName,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByProperty(java.lang.String, java.lang.Object, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByProperty(String propertyName,Object value,Class<?> persistentClass) {
		return (X)findUniqueByProperty(propertyName,value,EqRestriction.RestrictionName,persistentClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findUniqueByProperty(java.lang.String, java.lang.Object, java.lang.String, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByProperty(String propertyName,Object value,String restrictionName,Class<?> persistentClass) {
		Criterion criterion = PropertyFilterRestrictionHolder.getCriterion(propertyName, value, restrictionName);
		return (X) createCriteria(persistentClass, criterion).uniqueResult();
	}
	
    /* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findPage(com.hstyle.framework.dao.core.PageRequest)
	 */
    public Page<T> findPage(PageRequest request){
    	return findPage(request,null);
    }
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findPage(com.hstyle.framework.dao.core.PageRequest, java.util.List)
	 */
	public Page<T> findPage(PageRequest request,List<PropertyFilter> filters) {
		return findPage(request,filters,this.entityClass);
	}
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findPage(com.hstyle.framework.dao.core.PageRequest, java.util.List, java.lang.Class)
	 */
	public <X> Page<X> findPage(PageRequest request,List<PropertyFilter> filters,Class<?> persistentClass) {
		Criteria c = createCriteria(filters, persistentClass);
		return findPageByCriteria(request,c);
	}
	/**
	 * 根据条件查询所有数据
	 */
	public List<T> findFilters(List<PropertyFilter> filters,Class<?> persistentClass){
		Criteria c = createCriteria(filters, persistentClass);
		return c.list();
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findPage(com.hstyle.framework.dao.core.PageRequest, java.lang.String, java.lang.String)
	 */
	public Page<T> findPage(PageRequest request,String expression,String matchValue) {
		
		return findPage(request, expression,matchValue,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findPage(com.hstyle.framework.dao.core.PageRequest, java.lang.String, java.lang.String, java.lang.Class)
	 */
	public <X> Page<X> findPage(PageRequest request,String expression,String matchValue,Class<?> persistentClass) {
		Criterion criterion = createCriterion(expression, matchValue);
		Criteria criteria = createCriteria(persistentClass,criterion);
		return findPageByCriteria(request, criteria);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findPage(com.hstyle.framework.dao.core.PageRequest, java.lang.String[], java.lang.String[])
	 */
	public Page<T> findPage(PageRequest request,String[] expressions,String[] matchValues) {
		return findPage(request, expressions,matchValues,this.entityClass);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findPage(com.hstyle.framework.dao.core.PageRequest, java.lang.String[], java.lang.String[], java.lang.Class)
	 */
	public <X> Page<X> findPage(PageRequest request,String[] expressions,String[] matchValues,Class<?> persistentClass) {
		Criteria criteria = createCriteria(expressions, matchValues, persistentClass);
		return findPageByCriteria(request, criteria);
	}
	
	/**
	 * 根据分页参数与Criteria获取分页对象,辅助函数
	 * 
	 * @param request 分页请求参数
	 * @param c Criteria对象
	 * 
	 * @return {@link Page}
	 */
	protected <X> Page<X> findPageByCriteria(PageRequest request, Criteria c) {

		Page<X> page = new Page<X>(request);
		
		if (request == null) {
			return page;
		}
		
		if (request.isCountTotal()) {
			long totalCount = countCriteriaResult(c);
			page.setTotalItems(totalCount);
		}
		
		setPageRequestToCriteria(c, request);

		List result = c.list();
		page.setResult(result);
		
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findPage(com.hstyle.framework.dao.core.PageRequest, java.lang.String, java.lang.Object)
	 */
	public <X> Page<X> findPageByQuery(PageRequest request,String queryString,Object... values) {
		String hql=queryString;
		Page<X> page = createQueryPage(request, queryString, values);
		if (request.isOrderBySetted()) {
			hql = setOrderParameterToHql(queryString, request);
		}
		Query q = createQuery(hql, values);

		setPageParameterToQuery(q, request);

		List result = q.list();
		page.setResult(result);
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.AdvancedDao#findPage(com.hstyle.framework.dao.core.PageRequest, java.lang.String, java.util.Map)
	 */
	public <X> Page<X> findPageByQuery(PageRequest request, String queryString,Map<String,Object> values) {
		String hql=queryString;
		Page<X> page = createQueryPage(request, queryString, values);
		if (request.isOrderBySetted()) {
			hql = setOrderParameterToHql(queryString, request);
		}
		Query q = createQuery(hql, values);

		setPageParameterToQuery(q, request);

		List result = q.list();
		page.setResult(result);
		return page;
	}
	
	/**
	 * 通过分页请求参数和HQL创建分页对象
	 * 
	 * @param pageRequest 分页请求参数
	 * @param queryString HQL
	 * @param values 值
	 * 
	 * @return {@link Page}
	 */
	protected <X> Page<X> createQueryPage(PageRequest pageRequest, String queryString, Object... values) {

		Page<X> page = new Page<X>(pageRequest);
		
		if (pageRequest == null) {
			return page;
		}
		
		if (pageRequest.isCountTotal()) {
			long totalCount = countHqlResult(queryString, values);
			page.setTotalItems(totalCount);
		}

		
		
		return page;
	}
	
	/**
	 * 在HQL的后面添加分页参数定义的orderBy, 辅助函数.
	 */
	protected String setOrderParameterToHql( String hql, PageRequest pageRequest) {
		StringBuilder builder = new StringBuilder(hql);
		builder.append(" order by");

		for (Sort orderBy : pageRequest.getSort()) {
			builder.append(String.format(" %s.%s %s,", DEFAULT_ALIAS,orderBy.getProperty(), orderBy.getDirection()));
		}

		builder.deleteCharAt(builder.length() - 1);

		return builder.toString();
	}
	
	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery( Query q, PageRequest pageRequest) {
		q.setFirstResult(pageRequest.getOffset());
		q.setMaxResults(pageRequest.getPageSize());
		return q;
	}
	
	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 * 
	 * @param c Hibernate Criteria
	 * @param pageRequest 分页请求参数
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria setPageRequestToCriteria( Criteria c,  PageRequest pageRequest) {
		Assert.isTrue(pageRequest.getPageSize() > 0, "分页大小必须大于0");

		c.setFirstResult(pageRequest.getOffset());
		c.setMaxResults(pageRequest.getPageSize());

		if (pageRequest.isOrderBySetted()) {
			for (Sort sort : pageRequest.getSort()) {
				setOrderToCriteria(c,sort.getProperty(),sort.getDirection());
			}
		}
		return c;
	}



	@Override
	public boolean isUnique(T entity) {
		Criteria criteria=createCriteria().add(Example.create(entity));
		return countCriteriaResult(criteria)>0?false:true;
	}

	@Override
	public Page<T> findByEntityPaged(T entity, PageRequest pageRequest) {
		return findByEntityPaged(entity, pageRequest, false);
	}

	@Override
	public Page<T> findByEntityPaged(T entity, PageRequest pageRequest,
			boolean isLike) {
		Criteria criteria = createCriteria();
		Example example=Example.create(entity).excludeZeroes();
		if(isLike)
			example.enableLike(MatchMode.ANYWHERE);
		criteria.add(example);
		return findPageByCriteria(pageRequest, criteria);
	}
	public Map<Integer, List> callProc(final String procSqlName,final Object...objects) {
		return this.getSession().doReturningWork(new ReturningWork<Map<Integer, List>>() {

			@Override
			public Map<Integer, List> execute(Connection connection)
					throws SQLException {
				Map<Integer, List> resultMap=new HashMap<Integer, List>();
				CallableStatement proc=connection.prepareCall(procSqlName);
				for (int i = 0; i < objects.length; i++) {
					proc.setObject(i+1, objects[i]);
				}
				
				
				boolean isResult=proc.execute();
				boolean done=false;
				int index=0;
				while (!done) {
					if(isResult){
						ResultSet rst=proc.getResultSet();
						List<Map<String, Object>>  rows=new ArrayList<Map<String,Object>>();
						while (rst.next()) {
							 Map<String, Object> row=new HashMap<String, Object>();
							 for (int i = 1; i <= rst.getMetaData().getColumnCount(); i++) {
								 row.put(rst.getMetaData().getColumnLabel(i), rst.getObject(i));
							 }
							 rows.add(row);
						}
						resultMap.put(index, rows);
						index++;
					}else {
						if(proc.getUpdateCount()==-1)
							done=true;
					}
					isResult=proc.getMoreResults();
				}
				return resultMap;
			}
		});
	}
}
