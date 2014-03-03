/**
 * 
 */
package com.hstyle.admin.service;

import java.util.List;

import com.hstyle.admin.model.Dictionary;
import com.hstyle.admin.model.DictionaryMapping;
import com.hstyle.admin.model.DictionaryType;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.service.GenericService;

/**
 * @author Administrator
 *
 */
public interface DictionaryService extends GenericService<DictionaryType, Long> {
	
	/**
	 * 根据字典类型代码查找字典
	 * @param code
	 * @return 字典值集合
	 */
	List<Dictionary> getDictionariesByCode(String code);
	
	/**
	 * 根据字段代码和名称查找字典
	 * @param code
	 * @param name
	 * @return
	 */
	Page<DictionaryType> listDictionaryType(PageRequest pageRequest , DictionaryType dictionaryType);
	
	/**
	 * 保存或者更新字典{子信息}
	 * @param dictionary
	 */
	void saveOrUpdateDictionary(Dictionary dictionary);

	/**
	 * 根据外键查询所有子项内容
	 * @return
	 */
	List<Dictionary> getDictionariesByTypeid(Long parentid);
	
	/**
	 * 根据id逻辑删除数据字典{主表}信息
	 * @param ids
	 */
	void delDictType(String ids);
	
	/**
	 * 根据id逻辑删除数据字典{子表}信息
	 * @param ids
	 */
	void delDict(String ids);
	
	/**
	 * 根据id逻辑删除数据字典{映射表}信息
	 * @param ids
	 */
	void delMapping(String ids);
	
	/**
	 * 根据外键查询所有子项信息
	 * @param code
	 * @return
	 */
	List<DictionaryMapping> getMappingByDictId(Long parentid);
	
	/**
	 * 保存或者更新映射表信息
	 * @param dictionaryMapping
	 */
	void saveOrUpdateMapping(DictionaryMapping dictionaryMapping); 
	
	/**
	 * 根据主表code查询子表信息
	 * @return
	 */
	List<Dictionary> findDictionaryByParentCode(String parentCode);
	
	/**
	 * 获得字典项的名称
	 * @param type 字典类型
	 * @param key   字典key值
	 * @return 名称
	 */
	String getName(String type,String key);
	
}
