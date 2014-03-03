/**
 * 
 */
package com.hstyle.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.model.Entity;
import com.hstyle.framework.model.IdEntity;

/**
 * 业务接口,定义基本的CRUD操作;
 * 
 * @author Administrator
 * 
 */
public interface GenericService<T extends Entity, ID extends Serializable> {
	
	/**
	 * 新增业务实体
	 * @param entity
	 */
	public void add(T entity);
	
	/**
	 * 批量增加业务实体
	 * @param list
	 */
	public void addAll(List<T> list);
	

	/**
	 * 根据主键ID删除业务实体
	 * 
	 * @param id
	 */
	public void delete(ID id);
	/**
	 * 
	* @Title: delete
	* @Description: TODO(删除业务实体)
	* @param @param id
	* @param @param clazz    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void delete(ID id,Class clazz);
	
	/**
	 * 按id批量删除对象
	 * 
	 * @param ids 主键ID集合
	 */
	public void deleteAll(List<ID> ids);
	
	
	/**
	 * 修改业务实体
	 * 
	 * @param entity
	 */
	public void update(T entity);
	/**
	 * 
	* @Title: update
	* @Description: TODO(只更新限定的属性)
	* @param @param enity
	* @param @param property    设定文件
	* @return void    返回类型
	* @throws
	 */
	public void update(T enity,String[] property)throws RuntimeException;
	
	/**
	 * 批量修改业务实体
	 * @param entities 业务实体集合
	 */
	public void updateAll(List<T> entities);
	
	/**
	 * 新增或修改业务实体
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * 批量新增或修改多个实体
	 * @param entities
	 */
	public void saveAll(List<T> entities);
	
	/**
	 * 按PK获取对象实体.如果找不到对象或者id为null值时，返回null
	 * 
	 * @param id 主键ID
	 * 
	 */
	T get(ID id);

	/**
	 * 根据实体类型获取实体
	 * @param clz
	 * @param id
	 * @return
	 */
	public <X> X get(Class<X> clz,ID id);
	/**
	 * 按ID列表获取对象列表.
	 * 
	 * @param ids 主键ID集合 
	 * @return List
	 */
	List<T> get(Collection<ID> ids);

	/**
	 * 按PK列表获取对象列表.
	 * 
	 * @param ids 主键ID数据
	 * 
	 * @return List
	 */
	List<T> get(ID[] ids);

	/**
	 * 查询所有
	 * 
	 * @return list
	 */
	public List<T> findAll();

	/**
	 * 根据业务实体属性值联合查询
	 * 
	 * @param entity
	 * @return list
	 */
	public List<T> findByEntity(T entity);
	
	/**
	 * 分页查询所有记录
	 * @param pageRequest 分页参数对象
	 * @return page
	 */
	public Page<T> findAll(PageRequest pageRequest);
	
	/**
	 * 根据业务实体属性值分页查询
	 * @param entity
	 * @param pageable 分页属性
	 * @return page 分页对象
	 */
	public Page<T> findByEntity(T entity,PageRequest pageable) ;
	
	/**
	 * 复杂条件查询
	 * @param pageRequest 分页请求参数
	 * @param filters  属性过滤器
	 * @return
	 */
	public Page<T> findPage(PageRequest pageRequest,List<PropertyFilter> filters);
	
	/**
	 * 
	* @Title: findPage
	* @Description: TODO( 复杂条件查询,采用非固定的对象类)
	* @param @param pageRequest
	* @param @param filters
	* @param @param clazz
	* @param @return    设定文件
	* @return Page<T>    返回类型
	* @throws
	 */
	public Page<T> findPage(PageRequest pageRequest,List<PropertyFilter> filters,Class clazz);
	/**
	 * 
	* @Title: findFilters
	* @Description: TODO(查询符合条件的所有数据)
	* @param @param filters
	* @param @param clazz
	* @param @return    设定文件
	* @return List<T>    返回类型
	* @throws
	 */
	public List<T> findFilters(List<PropertyFilter> filters,Class clazz);
	/**
	 * 全文检索
	 * @param searchTerm 查询关键字
	 * @param clazz   实体类
	 * @return list
	 */
	List<T> search(String searchTerm, Class clazz);
}
