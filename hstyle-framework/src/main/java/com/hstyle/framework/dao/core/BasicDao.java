package com.hstyle.framework.dao.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public interface BasicDao<T, PK extends Serializable> {

	/**
	 * 新增对象.
	 * 
	 * @param entity orm实体
	 */
	void insert(T entity);

	/**
	 * 批量新增对象
	 * 
	 * @param list orm实体集合
	 */
	void insertAll(List<T> list);

	/**
	 * 更新对象
	 * @param entity orm实体
	 */
	void update(T entity);
	/**
	 * 
	* @Title: update
	* @Description: TODO(只更新需要的字段)
	* @param @param entity
	* @param @param properties    设定文件
	* @return void    返回类型
	* @throws
	 */
	void update(T entity,String[] properties)throws RuntimeException;
	/**
	 * 通用修改实体对象
	 * @param object
	 */
	void updateObject(Object object);
	
	/**
	 * 批量修改
	 * @param objects
	 */
	void updateObjects(List objects);

	/**
	 * 批量更新对象
	 * @param list orm实体集合
	 */
	void updateAll(List<T> list);

	/**
	 * 新增或修改对象
	 * 
	 * @param entity orm实体
	 */
	void save(T entity);
	
	/**
	 * 通用保存实体对象
	 * @param object
	 */
	void saveObject(Object object);
	
	/**
	 * 批量保存实体
	 * @param objects
	 */
	void saveObjects(List objects);

	/**
	 * 保存或更新全部对象
	 * 
	 * @param list orm实体集合
	 */
	void saveAll(List<T> list);

	/**
	 * 删除对象.
	 * 
	 * @param entity 对象必须是session中的对象或含PK属性的transient对象.
	 */
	void deleteByEntity(T entity);

	/**
	 * 按PK删除对象.
	 * 
	 * @param id 主键ID
	 */
	void delete(PK id);
	
	/**
	 * 按pk删除通用实体对象
	 * @param id
	 */
	<X> void  deleteObject(Class<X> clz,PK id);

	/**
	 * 按PK批量删除对象
	 * 
	 * @param ids 主键ID集合
	 */
	void deleteAll(List<PK> ids);

	/**
	 * 按orm实体集合删除对象 
	 * @param list
	 */
	void deleteAllByEntities(List<T> list);

	/**
	 * 按PK获取对象实体.如果找不到对象或者id为null值时，返回null,参考{@link Session#get(Class, Serializable)}
	 * 
	 * @see Session#get(Class, Serializable)
	 * 
	 * @param id 主键ID
	 * 
	 */
	T get(PK id);
	
	/**
	 * 根据实体类型获取实体
	 * @param clz
	 * @param id
	 * @return
	 */
	<X> X get(Class<X> clz,PK id);

	/**
	 * 按PK获取对象代理.如果id为null，返回null。参考{@link Session#load(Class, Serializable)}
	 * 
	 * @see Session#load(Class, Serializable)
	 * 
	 * @param id 主键ID
	 * 
	 */
	T load(PK id);
	


	/**
	 * 按PK列表获取对象列表.
	 * 
	 * @param ids 主键ID集合
	 * 
	 * @return List
	 */
	List<T> get(Collection<PK> ids);

	/**
	 * 按PK列表获取对象列表.
	 * 
	 * @param ids 主键ID数据
	 * 
	 * @return List
	 */
	List<T> get(PK[] ids);

	/**
	 * 获取全部对象.
	 * 
	 * @return List
	 */
	List<T> getAll();

	/**
	 * 通过HQL查询全部。参数形式为命名参数形式
	 * <pre>
	 * from Object o where o.property1 = :property1 and o.property2 = :proerty2
	 * </pre>
	 * 
	 * @param queryString hql语句
	 * @param values 与属性名方式的hql值
	 * 
	 * @return List
	 */
	<X> List<X> findByQuery(String queryString, Map<String, Object> values);

	/**
	 * 通过HQL查询全部.参数形式为jdbc参数形式:
	 * <pre>
	 * //使用是jdbc参数风格(问号后面没有带顺序值)
	 * from object o where o.property = ? and o.property = ?
	 * </pre>
	 * 
	 * @param queryString hql语句
	 * @param values 可变长度的hql值
	 * 
	 * @return List
	 */
	<X> List<X> findByQuery(String queryString, Object... values);

	/**
	 * 通过queryNamed查询全部。参数形式为命名参数形式
	 * <pre>
	 * from Object o where o.property1 = :property1 and o.property2 = :proerty2
	 * </pre>
	 * 
	 * @param queryNamed queryNamed
	 * @param values 属性名参数规则
	 * 
	 * @return List
	 */
	<X> List<X> findByQueryNamed(String queryNamed, Map<String, Object> values);

	/**
	 * 通过queryNamed查询全部,参数形式为jdbc参数形式:
	 * <pre>
	 * //使用是jdbc参数风格(问号后面没有带顺序值)
	 * from object o where o.property = ? and o.property = ?
	 * </pre>
	 * 
	 * @param queryNamed queryNamed
	 * @param values 值
	 * 
	 * @return List
	 */
	<X> List<X> findByQueryNamed(String queryNamed, Object... values);

	/**
	 * 通过hql查询单个orm实体，参数形式为命名参数形式
	 * <pre>
	 * from Object o where o.property1 = :property1 and o.property2 = :proerty2
	 * </pre>
	 * 
	 * @param queryString hql
	 * @param values 以属性名的hql值
	 * 
	 * @return Object
	 */
	<X> X findUniqueByQuery(String queryString, Map<String, Object> values);

	/**
	 * 通过hql查询单个orm实体,参数形式为jdbc参数形式:
	 * <pre>
	 * //使用是jdbc参数风格(问号后面没有带顺序值)
	 * from object o where o.property = ? and o.property = ?
	 * </pre>
	 * 
	 * @param queryString hql
	 * @param values 可变长度的hql值
	 * 
	 * @return Object
	 */
	<X> X findUniqueByQuery(String queryString, Object... values);

	/**
	 * 通过QueryName查询单个orm实体,参数形式为命名参数形式
	 * <pre>
	 * from Object o where o.property1 = :property1 and o.property2 = :proerty2
	 * </pre>
	 * 
	 * @param queryName queryName
	 * @param values 属性名参数规则
	 * 
	 * @return Object
	 */
	<X> X findUniqueByQueryNamed(String queryNamed, Map<String, Object> values);

	/**
	 * 通过QueryName查询单个orm实体,参数形式为jdbc参数形式:
	 * <pre>
	 * //使用是jdbc参数风格(问号后面没有带顺序值)
	 * from object o where o.property = ? and o.property = ?
	 * </pre>
	 * 
	 * @param queryName queryName
	 * @param values 值
	 * 
	 * @return Object
	 */
	<X> X findUniqueByQueryNamed(String queryNamed, Object... values);

	/**
	 * 获取全部对象
	 * 
	 * @param orderByProperty 要排序的字段名称
	 * @param isAsc 是否顺序排序 true表示顺序排序，false表示倒序
	 * 
	 * @return List
	 */
	List<T> getAll(String orderByProperty, boolean isAsc);

	/**
	 * 获取实体的总记录数
	 * 
	 * @return int
	 */
	int entityCount();

	/**
	 * 将persistable对象 Object对象、代理对象、持久化对象，Collection 对象或为空的对象里的代理属性全部加载(该方法使用了Hibernate.initialize方法)
	 * 
	 * @param proxy 一个persistable对象 Object对象、代理对象、持久化对象，Collection 对象或为空的对象
	 * 
	 */
	void initProxyObject(Object proxy);



	/**
	 * 执行HQL进行批量修改/删除操作.成功后返回更新记录数,参数形式为命名参数形式:
	 * <pre>
	 * //使用的是命名参数风格
	 * update object o set o.property = :property where o.condition = :condition
	 * </pre>
	 * 
	 * @param values 命名参数,按名称绑定.
	 *            
	 * @return int
	 */
	int executeUpdate(String hql, Map<String, ?> values);

	/**
	 * 执行HQL进行批量修改/删除操作.成功后更新记录数,参数形式为jdbc参数形式:
	 * <pre>
	 * //使用是jdbc参数风格(问号后面没有带顺序值)
	 * update object o set o.property=? where o.condition = ?
	 * </pre>
	 * 
	 * @param values 参数值
	 *            
	 * @return int
	 */
	int executeUpdate(String hql, Object... values);

	/**
	 * 通过queryNamed执行HQL进行批量修改/删除操作.成功后返回更新记录数,参数形式为命名参数形式:
	 * <pre>
	 * //使用的是命名参数风格
	 * update object o set o.property = :property where o.condition = :condition
	 * </pre>
	 * 
	 * @param values 命名参数,按名称绑定.
	 *            
	 * @return int
	 */
	int executeUpdateByQueryNamed(String queryNamed, Map<String, ?> values);

	/**
	 * 通过queryNamed执行HQL进行批量修改/删除操作.成功后返回更新记录数,参数形式为jdbc参数形式:
	 * <pre>
	 * //使用是jdbc参数风格(问号后面没有带顺序值)
	 * update object o set o.property=? where o.condition = ?
	 * </pre>
	 * 
	 * @param values 参数值
	 *            
	 * @return int
	 */
	int executeUpdateByQueryNamed(String queryNamed, Object... values);
	
	/**
	 * 条件查询，默认根据业务实体的属性值进行联合查询，
	 *  
	 * @param entity
	 * @return List<v>
	 */
	List<T> findByEntity(T entity);
	
	/**
	 * 条件查询，默认根据业务实体的属性值进行联合查询并排序，
	 *  
	 * @param entity
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @return list<T>
	 */
	List<T> findByEntity(T entity,String orderBy);
	
	/**
	 * 条件查询，默认根据业务实体的属性值进行联合模糊查询并排序，
	 *  
	 * @param entity
	 * @param orderBy 排序表达式，规则为:属性名称_排序规则,如:property_asc或property_desc,可以支持多个属性排序，用逗号分割,如:"property1_asc,proerty2_desc",也可以"property"不加排序规则时默认是desc
	 * @param isLike 是否启用模糊查询
	 * @return List<T>
	 */
	List<T> findByEntity(T entity,String orderBy,boolean isLike);
	
	

}