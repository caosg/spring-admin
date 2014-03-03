package com.hstyle.framework.dao.core.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import com.hstyle.framework.dao.annotation.StateDelete;
import com.hstyle.framework.dao.core.BasicDao;
import com.hstyle.framework.dao.core.PageRequest.Sort;
import com.hstyle.framework.dao.enumeration.ExecuteMehtod;
import com.hstyle.framework.dao.strategy.CodeStrategy;
import com.hstyle.framework.dao.strategy.annotation.ConvertCode;
import com.hstyle.framework.dao.strategy.annotation.ConvertProperty;
import com.hstyle.framework.utils.CollectionUtils;
import com.hstyle.framework.utils.ReflectionUtils;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.jdbc.Work;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * 
 * Hibernate基础类,包含对Hibernate的CURD和其他Hibernate操作
 * 
 * @author 
 *
 * @param <T> 实体对象
 * @param <PK> 主键ID类型
 */
public class BasicHibernateDao<T,PK extends Serializable> implements BasicDao<T, PK>  {
	
	
	protected SessionFactory sessionFactory;

	protected Class<T> entityClass;
	
	protected final String DEFAULT_ALIAS = "X";
	
	private static Logger logger = LoggerFactory.getLogger(BasicHibernateDao.class); 
	/**
	 * 构造方法
	 */
	public BasicHibernateDao() {
		entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 构造方法
	 * 
	 * @param entityClass orm实体类型class
	 */
	public BasicHibernateDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	/**
	 * 构造方法
	 * 
	 * @param entityClass orm实体类型class
	 */
	public BasicHibernateDao(Class<T> entityClass,SessionFactory sessionFactory) {
		this.entityClass = entityClass;
		this.sessionFactory=sessionFactory;
	}

	/**
	 * 设置Hibernate sessionFactory
	 * 
	 * @param sessionFactory
	 */
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取Hibernate SessionFactory
	 * 
	 * @return {@link SessionFactory}
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * 取得当前Session.
	 * 
	 * @return {@link Session}
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 对修改orm实体前的处理, 如果orm实体不为null返回true,并且将该orm实体进行转码,否则返回flase，
	 * 
	 * @param entity orm实体
	 * @param executeMehtods 
	 * 
	 * @return boolean
	 */
	private boolean preproModifyEntity(T entity,ExecuteMehtod... executeMehtods) {
		
		if (entity == null) {
			
			return false;
		}
		
		convertObject(entity, executeMehtods);
		
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#insert(T)
	 */
	public void insert(T entity) {
		
		if (!preproModifyEntity(entity,ExecuteMehtod.Insert)) {
			return ;
		}
		getSession().save(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#insertAll(java.util.List)
	 */
	public void insertAll(List<T> list) {
		
		if (CollectionUtils.isEmpty(list)) {
			return ;
		}
		int count=0;
		for (Iterator<T> it = list.iterator(); it.hasNext();) {
			count++;
			insert(it.next());
			if(count%50==0){
				  getSession().flush();
				  getSession().clear();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#update(T)
	 */
	public void update(T entity) {
		if (!preproModifyEntity(entity,ExecuteMehtod.Update)) {
			return ;
		}
		getSession().update(entity);
	}
	
	@Override
	public void update(T entity,String[] properties)throws RuntimeException{
		String hql="update "+entity.getClass().getSimpleName()+" set ";
		StringBuffer sb=new StringBuffer();
		for(String child:properties){
			sb.append(","+child+"=:"+child);
		}
		getSession().createQuery(hql+sb.substring(1)+" where id=:id").setProperties(entity).executeUpdate();
	}
	public void updateObject(Object object){
		getSession().update(object);
	}
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#updateAll(java.util.List)
	 */
	public void updateAll(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return ;
		}
		int count=0;
		for (Iterator<T> it = list.iterator(); it.hasNext();) {
			count++;
			update(it.next());
			if(count%50==0){
			  getSession().flush();
			  getSession().clear();
			}
		}
	}
	
	@Override
	public void updateObjects(List objects) {
		if (CollectionUtils.isEmpty(objects)) {
			return ;
		}
		int count=0;
		//50条一批量提交
		for (Iterator it = objects.iterator(); it.hasNext();) {
			count++;
			updateObject(it.next());
			if(count%50==0){
			  flush();
			  clear();
			}
		}
		
	}

	@Override
	public void saveObjects(List objects) {
		if (CollectionUtils.isEmpty(objects)) {
			return ;
		}
		int count=0;
		//50条一批量提交
		for (Iterator it = objects.iterator(); it.hasNext();) {
			count++;
			saveObject(it.next());
			if(count%50==0){
			  flush();
			  clear();
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#save(T)
	 */
	public void save(T entity) {
		if (!preproModifyEntity(entity, ExecuteMehtod.Save)) {
			return ;
		}
		getSession().saveOrUpdate(entity);
	}
	
	public void saveObject(Object object){
		getSession().saveOrUpdate(object);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#saveAll(java.util.List)
	 */
	public void saveAll(List<T> list) {
		
		if (CollectionUtils.isEmpty(list)) {
			return ;
		}
		for (Iterator<T> it = list.iterator(); it.hasNext();) {
			save(it.next());
		}
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#deleteByEntity(T)
	 */
	public void deleteByEntity(T entity) {
		
		if (entity == null) {
			logger.warn("要删除的对象为:null");
			return ;
		}
		
		StateDelete stateDelete = ReflectionUtils.getAnnotation(entity.getClass(),StateDelete.class);
		if (stateDelete != null) {
			Object value = ConvertUtils.convert(stateDelete.value(), stateDelete.type().getValue());
			ReflectionUtils.invokeSetterMethod(entity, stateDelete.propertyName(), value);
			update(entity);
		} else {
			getSession().delete(entity);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#delete(PK)
	 */
	public void delete(PK id) {
		deleteByEntity(get(id));
	}
	
	public <X> void  deleteObject(Class<X> clz,PK id){
		X object=get(clz, id);
		if(object==null)
			return;
		getSession().delete(object);
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#deleteAll(java.util.List)
	 */
	public void deleteAll(List<PK> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return ;
		}
		for (Iterator<PK> it = ids.iterator(); it.hasNext();) {
			delete(it.next());
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#deleteAllByEntities(java.util.List)
	 */
	public void deleteAllByEntities(List<T> list) {
		if (!CollectionUtils.isEmpty(list)) {
			return ;
		}
		
		for (Iterator<T> it = list.iterator(); it.hasNext();) {
			deleteByEntity(it.next());
		}
	}
	

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#get(PK)
	 */
	@SuppressWarnings("unchecked")
	public T get(PK id) {
		
		if (id == null) {
			return null;
		}
		
		return (T) getSession().get(entityClass, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <X> X get(Class<X> clz, PK id) {
		if (id == null) {
			return null;
		}
		return (X) getSession().get(clz, id);
	}
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#load(PK)
	 */
	@SuppressWarnings("unchecked")
	public T load(PK id) {
		if (id == null) {
			return null;
		}
		
		return (T) getSession().load(entityClass, id);
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#get(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public List<T> get(Collection<PK> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		return createCriteria(Restrictions.in(getIdName(), ids)).list();
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#get(PK[])
	 */
	@SuppressWarnings("unchecked")
	public List<T> get(PK[] ids) {
		return createCriteria(Restrictions.in(getIdName(), ids)).list();
	}

	/**
	 * 取得对象的主键名.
	 * 
	 * @return String
	 */
	public String getIdName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}
	
	/**
	 * 获取实体名称
	 * 
	 * @return String
	 */
	public String getEntityName() {
		ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
		return meta.getEntityName();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#getAll()
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return createCriteria().list();
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#findByQuery(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByQuery(String queryString,Map<String,Object> values) {
		return createQuery(queryString, values).list();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#findByQuery(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByQuery(String queryString,Object... values) {
		return createQuery(queryString, values).list();
	}
	
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#findByQueryNamed(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByQueryNamed(String queryNamed,Map<String, Object> values) {
		return createQueryByQueryNamed(queryNamed, values).list();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#findByQueryNamed(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> findByQueryNamed(String queryNamed,Object... values) {
		return createQueryByQueryNamed(queryNamed, values).list();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#findUniqueByQuery(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByQuery(String queryString,Map<String, Object> values){
		return (X)createQuery(queryString, values).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#findUniqueByQuery(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByQuery(String queryString,Object... values){
		return (X)createQuery(queryString, values).uniqueResult();
	}
	

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#findUniqueByQueryNamed(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByQueryNamed(String queryNamed,Map<String, Object> values) {
		return (X)createQueryByQueryNamed(queryNamed, values).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#findUniqueByQueryNamed(java.lang.String, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUniqueByQueryNamed(String queryNamed,Object... values) {
		return (X) createQueryByQueryNamed(queryNamed, values).uniqueResult();
	}
	
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#getAll(java.lang.String, boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		Criteria c = createCriteria();
		setOrderToCriteria(c, isAsc ? orderByProperty+"_"+Sort.ASC :  orderByProperty+"_"+Sort.DESC);
		return c.list();
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#entityCount()
	 */
	public int entityCount() {
		return countHqlResult("from " + getEntityName() + " " + DEFAULT_ALIAS).intValue();
	}
	
	/**
	 * 根据Criterion可变数组创建Criteria对象
	 * 
	 * @param criterions 可变长度的Criterion数组
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(Criterion... criterions) {
		return createCriteria(this.entityClass,criterions);
	}
	
	/**
	 * 根据Criterion可变数组创建Criteria对象
	 * 
	 * @param criterions 可变长度的Criterion数组
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(String orderBy,Criterion... criterions) {
		return createCriteria(this.entityClass, orderBy,criterions);
	}
	
	/**
	 * 根据Criterion可变数组创建Criteria对象
	 * 
	 * @param persistentClass orm实体class
	 * @param criterions 可变长度的Criterion数组
	 * 
	 * @return {@link Criteria}
	 */
	protected Criteria createCriteria(Class<?> persistentClass,Criterion... criterions) {
		return createCriteria(persistentClass,StringUtils.EMPTY,criterions);
	}

	/**
	 * 根据Criterion可变数组创建Criteria对象
	 * 
	 * @param persistentClass orm实体class
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param criterions 可变长度的Criterion数组 
	 * 
	 * @return @return {@link Criteria}
	 */
	protected Criteria createCriteria(Class<?> persistentClass,String orderBy,Criterion... criterions) {
		
		Criteria criteria = getSession().createCriteria(persistentClass);
		
		for (Criterion criterion :criterions) {
			
			criteria.add(criterion);
		}
		setOrderToCriteria(criteria,orderBy);
		return criteria;
	}
	
	/**
	 * 根据DetachedCriteria创建Criteria对象
	 * 
	 * @param detachedCriteria Hibernate DetachedCriteria
	 * 
	 * @return @return {@link Criteria}
	 */
	protected Criteria createCriteria(DetachedCriteria detachedCriteria) {
		return detachedCriteria.getExecutableCriteria(getSession());
	}
	
	/**
	 * 根据Criterion可变数组创建DetachedCriteria对象
	 * 
	 * @param criterions 可变长度的Criterion数组 
	 * 
	 * @return @return {@link DetachedCriteria}
	 */
	protected DetachedCriteria createDetachedCriteria(Criterion... criterions) {
		return createDetachedCriteria(this.entityClass,null,criterions);
	}
	
	/**
	 * 根据Criterion可变数组创建DetachedCriteria对象
	 * 
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param criterions 可变长度的Criterion数组 
	 * 
	 * @return @return {@link DetachedCriteria}
	 */
	protected DetachedCriteria createDetachedCriteria(String orderBy,Criterion... criterions) {
		return createDetachedCriteria(this.entityClass,orderBy,criterions);
	}
	
	/**
	 * 根据Criterion可变数组创建DetachedCriteria对象
	 * 
	 * @param persistentClass orm实体class
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param criterions 可变长度的Criterion数组 
	 * 
	 * @return @return {@link DetachedCriteria}
	 */
	protected DetachedCriteria createDetachedCriteria(Class<?> persistentClass,String orderBy,Criterion... criterions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
		for (Criterion criterion :criterions) {
			
			criteria.add(criterion);
		};
		CriteriaImpl criteriaImpl = ReflectionUtils.getFieldValue(criteria, "impl");
		setOrderToCriteria(criteriaImpl,orderBy);
		return criteria;
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象,参数形式为命名参数形式:
	 * <pre>
	 * //使用的是命名参数风格
	 * from object o where o.condition = :condition
	 * </pre>
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 *            
	 * @return {@link Query}           
	 * 
	 */
	protected Query createQuery( String queryString, Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}
	
	/**
	 * 根据hql创建Hibernate Query对象，参数形式为jdbc参数形式:
	 * <pre>
	 * //使用是jdbc参数风格(问号后面没有带顺序值)
	 * from object o where o.property = ? and o.property = ?
	 * </pre>
	 * 
	 * @param queryString hql
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 *            
	 * @return {@link Query}
	 */
	protected Query createQuery(String queryString, Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		setQueryValues(query, values);
		return query;
	}
	
	
	/**
	 * 通过queryNamed 创建Query,参数形式为命名参数形式:
	 * <pre>
	 * //使用的是命名参数风格
	 * from object o where o.condition = :condition
	 * </pre>
	 * 
	 * @param queryNamed queryNamed
	 * @param values 属性名参数规则
	 * 
	 * @return {@link Query}
	 */
	protected Query createQueryByQueryNamed(String queryNamed,Map<String, Object> values) {
		Query query = getSession().getNamedQuery(queryNamed);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 通过queryNamed创建Query。参数形式为jdbc参数形式:
	 * <pre>
	 * //使用是jdbc参数风格(问号后面没有带顺序值)
	 * from object o where o.property = ? and o.property = ?
	 * </pre>
	 * 
	 * @param queryNamed queryNamed
	 * @param values 值
	 * 
	 * @return {@link Query}
	 */
	protected Query createQueryByQueryNamed(String queryNamed,Object... values) {
		Assert.hasText(queryNamed, "QueryNamed不能为空");
		Query query = getSession().getNamedQuery(queryNamed);
		setQueryValues(query, values);
		return query;
	}
	
	
	/**
	 * 根据查询HQL与参数列表创建Query对象，数形式为命名参数形式:
	 * <pre>
	 * //使用的是命名参数风格
	 * from object o where o.condition = :condition
	 * </pre>
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 *            
	 * @return {@link Query}           
	 * 
	 */
	protected SQLQuery createSQLQuery( String queryString, Map<String, ?> values) {
		Assert.hasText(queryString, "queryString不能为空");
		SQLQuery query = getSession().createSQLQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query.addEntity(entityClass);
	}

	/**
	 * 根据查询SQL与参数列表创建SQLQuery对象，参数形式为jdbc参数形式:
	 * <pre>
	 * //使用是jdbc参数风格(问号后面没有带顺序值)
	 * from object o where o.property = ? and o.property = ?
	 * </pre>
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 *            
	 * @return {@link SQLQuery}
	 */
	protected SQLQuery createSQLQuery( String queryString,  Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		SQLQuery query = getSession().createSQLQuery(queryString);
		setQueryValues(query, values);
		return query.addEntity(entityClass);
	}
	
	
	/**
	 * 通过orm实体属性名称和排序值向Criteria设置排序方式
	 * 
	 * @param criteria Criteria设置排序方式,
	 * @param property orm实体属性名称
	 * @param dir 排序方法，"asc"或"desc"
	 */
	protected void setOrderToCriteria(Criteria criteria,String property,String dir) {
		if(StringUtils.equals(dir.toLowerCase(), Sort.ASC)) {
			criteria.addOrder(Order.asc(property));
		} else {
			criteria.addOrder(Order.desc(property));
		}
	}

	
	/**
	 * 设置参数值到query的hql中,该参数是属于jdbc风格的参数
	 *
	 * @param query Hibernate Query
	 * @param values 参数值可变数组
	 */
	protected void setQueryValues(Query query ,Object... values) {
		if (ArrayUtils.isEmpty(values)) {
			return ;
		}
		
		for (Integer i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
	}
	
	/**
	 * 
	 * 将对象集合执行转码操作
	 * 
	 * @param source 要转码的对象集合
	 * @param executeMehtods 在什么方法进行转码
	 */
	protected void convertObjects(List<Object> source,ExecuteMehtod... executeMehtods) {
		for (Iterator<Object> it = source.iterator(); it.hasNext();) {
			convertObject(it.next(), executeMehtods);
		}
	}
	
	/**
	 * 
	 * 将对象执行转码操作
	 * 
	 * @param source 要转码的对象
	 * @param executeMehtods 在什么方法进行转码
	 */
	protected void convertObject(Object source,ExecuteMehtod...executeMehtods) {
		if (executeMehtods == null) {
			return ;
		}
		
		ConvertCode convertCode = ReflectionUtils.getAnnotation(source.getClass(),ConvertCode.class);
		
		if (convertCode == null) {
			return ;
		}
		
		for (ExecuteMehtod em:executeMehtods) {
			if (convertCode.executeMehtod().equals(em)) {
				for (ConvertProperty convertProperty : convertCode.convertPropertys()) {
					
					CodeStrategy strategy = ReflectionUtils.newInstance(convertProperty.strategyClass());
					
					for (String property :convertProperty.propertyNames()) {
						
						Object fromValue = ReflectionUtils.invokeGetterMethod(source, convertCode.fromProperty());
						Object convertValue = strategy.convertCode(fromValue,property);
						ReflectionUtils.invokeSetterMethod(source, property, convertValue);
						
					}
				}
			}
		}
		
	}
	
	/**
	 * 通过排序表达式向Criteria设置排序方式,
	 * @param criteria Criteria
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 */
	protected void setOrderToCriteria(Criteria criteria, String orderBy) {
		if (StringUtils.isEmpty(orderBy)) {
			return ;
		}
		
		String[] orderBys = null;
		if (StringUtils.contains(orderBy,",")) {
			orderBys = StringUtils.splitByWholeSeparator(orderBy, ",");
		} else {
			orderBys = new String[]{orderBy};
		}
		
		for (String ob: orderBys) {
		
			if (StringUtils.contains(ob, "_")) {
				String[] temp = StringUtils.splitByWholeSeparator(ob, "_");
				String property = temp[0];
				String dir = temp[1];
				if (!StringUtils.equals(dir.toLowerCase(), Sort.ASC) && !StringUtils.equals(dir.toLowerCase(), Sort.DESC)) {
					throw new IllegalAccessError("orderBy规则错误，当前为:" + dir + " 应该为:ASC或者:DESC");
				}
				setOrderToCriteria(criteria,property,dir);
			} else {
				setOrderToCriteria(criteria,ob,Sort.DESC);
			}
		}
	}
	
	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.使用命名方式参数
	 * 
	 * <pre>
	 * 	from object o where o.property = :proprty and o.property = :proprty
	 * </pre>
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 * 
	 * @param queryString HQL
	 * @param values 值
	 * 
	 * @return long
	 */
	protected Long countHqlResult( String queryString,  Map<String, ?> values) {
		String countHql = prepareCountHql(queryString);

		try {
			return (Long)createQuery(queryString, values).uniqueResult();
		} catch (Exception e) {
			throw new RuntimeException("hql不能自动计算总是:"+ countHql, e);
		}
	}
	
	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.(使用jdbc方式参数)
	 * 
	 * <pre>
	 * 	from object o where o.property = ? and o.property = ?
	 * </pre>
	 * 
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 * 
	 * @param queryString HQL
	 * @param values 值
	 * 
	 * @return long
	 */
	protected Long countHqlResult( String queryString,  Object... values) {
		String countHql = prepareCountHql(queryString);

		try {
			return (Long)createQuery(countHql, values).uniqueResult();
		} catch (Exception e) {
			throw new RuntimeException("hql不能自动计算总数:"+ countHql, e);
		}
	}
	
	
	/**
	 * 绑定计算总数HQL语句,返回绑定后的hql字符串
	 * 
	 * @param orgHql hql
	 * 
	 * @return String
	 */
	private String prepareCountHql(String orgHql) {
		String countHql = "select count (*) " + removeSelect(removeOrders(orgHql));
		return countHql;
	}
	
	/**
	 * 移除from前面的select 字段 返回移除后的hql字符串
	 * @param hql 
	 * @return String
	 */
	private String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}
	
	/**
	 * 删除hql中的 orderBy的字段,返回删除后的新字符串
	 * 
	 * @param hql
	 * 
	 * @return String
	 */
	private String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 * 
	 * @param query Hibernate Query 接口
	 * 
	 * @return {@link Query};
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
	 * 
	 * @param criteria Hibernate Criteria 接口
	 * 
	 * @return {@link Criteria}
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}
	
	/**
	 * 根据HQL创建迭代器
	 * 
	 * @param queryString hql
	 * @param values 值
	 * 
	 * @return Iterator
	 */
	@SuppressWarnings("unchecked")
	public Iterator<T> iterator(String queryString,Object... values) {
		Query query = createQuery(queryString, values);
		return distinct(query).iterate();
		
	}
	
	/**
	 * 根据Criterion创建迭代器
	 * 
	 * @param criterions 可变的Criterion参数
	 * 
	 * @return Iterator
	 */
	@SuppressWarnings("unchecked")
	public Iterator<T> iterator(Criterion...criterions) {
		return distinct(createCriteria(criterions)).list().iterator();
	}
	
	/**
	 * 立即关闭迭代器而不是等待session.close的时候才关闭
	 * @param it 要关闭的迭代器
	 * 
	 */
	public void closeIterator(Iterator<?> it) throws HibernateException {
		Hibernate.close(it);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#initProxyObject(java.lang.Object)
	 */
	public void initProxyObject(Object proxy) {
		Hibernate.initialize(proxy);
	}

	/**
	 * Flush当前Session.
	 */
	public void flush() {
		getSession().flush();
	}
	
	/**
	 * 如果session中存在相同持久化识别的实例，用给出的对象的状态覆盖持久化实例
	 * 
	 * @param entity 持久化实例
	 */
	public void merge(T entity) {
		if (!preproModifyEntity(entity)) {
			return ;
		}
		getSession().merge(entity);
	}
	
	/**
	 * 如果session中存在相同持久化识别的实例，用给出的对象的状态覆盖持久化实例
	 * 
	 * @param entity 持久化实例
	 * @param entityName 持久化对象名称
	 */
	public void merge(String entityName,T entity) {
		if (!preproModifyEntity(entity)) {
			return ;
		}
		getSession().merge(entityName, entity);
	}
	
	/**
	 * 刷新操作对象
	 * 
	 * @param entity 操作对象
	 */
	public void refresh(T entity) {
		if (!preproModifyEntity(entity)) {
			return ;
		}
		getSession().refresh(entity);
	}
	
	/**
	 * 刷新操作对象
	 * 
	 * @param entity 操作对象
	 * @param lockOptions Hibernate LockOptions
	 */
	public void refresh(T entity,LockOptions lockOptions) {
		if (!preproModifyEntity(entity)) {
			return ;
		}
		
		if (lockOptions == null) {
			refresh(entity);
		} else {
			getSession().refresh(entity, lockOptions);
		}
	}
	
	/**
	 * 把操作对象在缓存区中直接清除
	 * 
	 * @param entity 操作对象
	 */
	public void evict(T entity) {
		if (!preproModifyEntity(entity)) {
			return ;
		}
		getSession().evict(entity);
	}
	
	/**
	 * 把session所有缓存区的对象全部清除，但不包括正在操作中的对象
	 */
	public void clear() {
		getSession().clear();
	}
	
	/**
	 * 对于已经手动给ID主键的操作对象进行insert操作
	 * 
	 * @param entity 操作对象
	 * @param replicationMode 创建策略
	 */
	public void replicate(T entity, ReplicationMode replicationMode) {
		if (!preproModifyEntity(entity) || replicationMode == null) {
			return ;
		}
		getSession().replicate(entity, replicationMode);
	}
	
	/**
	 * 对于已经手动给ID主键的操作对象进行insert操作
	 * 
	 * @param entityName 操作对象名称
	 * @param entity 操作对象
	 * @param replicationMode 创建策略
	 */
	public void replicate(String entityName,T entity, ReplicationMode replicationMode) {
		if (!preproModifyEntity(entity) || replicationMode == null) {
			return ;
		}
		getSession().replicate(entityName,entity, replicationMode);
	}
	
	/**
	 * 把一个瞬态的实例持久化，但很有可能不能立即持久化实例，可能会在flush的时候才会持久化
	 * 当它在一个transaction外部被调用的时候并不会触发insert。
	 * 
	 * @param entity 瞬态的实例
	 */
	public void persist(T entity) {
		if (!preproModifyEntity(entity)) {
			return ;
		}
		getSession().persist(entity);
	}
	
	/**
	 * 把一个瞬态的实例持久化，但很有可能不能立即持久化实例，可能会在flush的时候才会持久化
	 * 当它在一个transaction外部被调用的时候并不会触发insert。
	 * 
	 * @param entity 瞬态的实例
	 * @param entityName 瞬态的实例名称
	 */
	public void persist(String entityName, T entity) {
		if (!preproModifyEntity(entity)) {
			return ;
		}
		getSession().persist(entityName,entity);
	}
	
	/**
	 * 从当前Session中获取一个能够操作JDBC的Connection并执行想要操作的JDBC语句
	 * 
	 * @param work Hibernate Work
	 */
	public void doWork(Work work) {
		getSession().doWork(work);
	}
	
	/**
	 * 判断entity实例是否已经与session缓存关联,是返回true,否则返回false
	 * 
	 * @param entity 实例
	 * 
	 * @return boolean
	 */
	public boolean contains(Object entity) {
		return getSession().contains(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#executeUpdate(java.lang.String, java.util.Map)
	 */
	public int executeUpdate(String hql,  Map<String, ?> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#executeUpdate(java.lang.String, java.lang.Object)
	 */
	public int executeUpdate(String hql,  Object... values) {
		return createQuery(hql, values).executeUpdate();
	}
	

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#executeUpdateByQueryNamed(java.lang.String, java.util.Map)
	 */
	public int executeUpdateByQueryNamed(String queryNamed,Map<String, ?> values) {
		return createQueryByQueryNamed(queryNamed, values).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.hstyle.framework.dao.core.hibernate.BasicDao#executeUpdateByQueryNamed(java.lang.String, java.lang.Object)
	 */
	public int executeUpdateByQueryNamed(String queryNamed,Object... values) {
		return createQueryByQueryNamed(queryNamed, values).executeUpdate();
	}

	@Override
	public List<T> findByEntity(T entity) {		
		return findByEntity(entity, null);
	}

	@Override
	public List<T> findByEntity(T entity, String orderBy) {
		
		return findByEntity(entity, orderBy, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByEntity(T entity, String orderBy, boolean isLike) {
		Criteria criteria = getSession().createCriteria(entity.getClass());
		Example example=Example.create(entity).excludeZeroes();
		if(isLike)
			example.enableLike(MatchMode.ANYWHERE);
		criteria.add(example);
		setOrderToCriteria(criteria, orderBy);
		return criteria.list();
	}

	

	

	
	

}
