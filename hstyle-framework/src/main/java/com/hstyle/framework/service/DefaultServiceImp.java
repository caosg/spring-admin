/**
 * 
 */
package com.hstyle.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hstyle.framework.dao.core.AdvancedDao;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.model.Entity;

/**
 * @author Administrator
 * 
 */
@Transactional
public class DefaultServiceImp<T extends Entity, ID extends Serializable>
		implements GenericService<T, ID> {
	protected Logger log = LoggerFactory.getLogger(getClass());
	protected AdvancedDao<T, ID> dao;

	public DefaultServiceImp() {
	}

	public DefaultServiceImp(AdvancedDao<T, ID> genericDao) {
		this.dao = genericDao;
	}

	@Override
	public void add(T entity) {
		dao.insert(entity);

	}

	@Override
	public void addAll(List<T> list) {
		dao.insertAll(list);

	}

	@Override
	public void delete(ID id) {
		dao.delete(id);

	}
	@Override
	public void delete(ID id,Class clazz){
		dao.deleteObject(clazz, id);
	}
	@Override
	public void deleteAll(List<ID> ids) {
		dao.deleteAll(ids);

	}

	@Override
	public void update(T entity) {
		dao.update(entity);
	}
	@Override
	public void update(T enity,String[] property){
		dao.update(enity,property);
	}
	
	@Override
	public void updateAll(List<T> entities) {
		dao.updateAll(entities);

	}
	
	@Override
	public void save(T entity) {
		dao.save(entity);
	}

	@Override
	public void saveAll(List<T> entities) {
		dao.saveAll(entities);
		
	}

	@Override
	public T get(ID id) {

		return dao.get(id);
	}
	@Override
	public <X> X get(Class<X> clz,ID id){
		return dao.get(clz, id);
	}
	@Override
	public List<T> get(Collection<ID> ids) {

		return dao.get(ids);
	}

	@Override
	public List<T> get(ID[] ids) {
		return dao.get(ids);
	}

	@Override
	public List<T> findAll() {

		return dao.getAll();
	}

	@Override
	public List<T> findByEntity(T entity) {
		return dao.findByEntity(entity);
	}

	@Override
	public Page<T> findAll(PageRequest pageable) {
		return dao.findPage(pageable);
	}

	@Override
	public Page<T> findByEntity(T entity, PageRequest pageable) {
		return dao.findByEntityPaged(entity, pageable);
	}

	@Override
	public List<T> search(String searchTerm, Class clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<T> findPage(PageRequest pageRequest,
			List<PropertyFilter> filters) {
		return dao.findPage(pageRequest, filters);
	}
	@Override
	public List<T> findFilters(List<PropertyFilter> filters,Class clazz){
		return dao.findFilters(filters, clazz);
	}
	
	@Override
	public Page<T> findPage(PageRequest pageRequest,List<PropertyFilter> filters,Class persistentClass){
		return dao.findPage(pageRequest, filters, persistentClass);
	}

}
