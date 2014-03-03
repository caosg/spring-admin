/**
 * 
 */
package com.hstyle.admin.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hstyle.admin.dao.DictionaryDao;
import com.hstyle.admin.dao.DictionaryMappingDao;
import com.hstyle.admin.dao.DictionaryTypeDao;
import com.hstyle.admin.model.Dictionary;
import com.hstyle.admin.model.DictionaryMapping;
import com.hstyle.admin.model.DictionaryType;
import com.hstyle.admin.service.DictionaryService;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;
import com.hstyle.framework.service.DefaultServiceImp;

/**
 * 数据字典业务实现
 * @author jiaoyuqiang
 *
 */
@Service("dictionaryService")
public class DictionaryServiceImp extends DefaultServiceImp<DictionaryType, Long> implements
		DictionaryService {
	@Autowired
	private DictionaryDao dictionaryDao;
	private DictionaryTypeDao dictionaryTypeDao;
	@Autowired
	private DictionaryMappingDao dictionaryMappingDao;
	
	@Cacheable(value="sys-cache")	
	public List<Dictionary> getDictionariesByCode(String code) {
		return dictionaryDao.findByQueryNamed(Dictionary.FindByTypeCode, code);
	}

	/**
	 * 分页查询数据字典
	 */
	@Override
	public Page<DictionaryType> listDictionaryType(PageRequest pageRequest , DictionaryType dictionaryType) {
		return dictionaryTypeDao.findByEntityPaged(dictionaryType, pageRequest);
	}
	
	/**
	 * 保存或者更新字典{子信息}
	 * @param dictionary
	 */
	@Override
	@CacheEvict(value="sys-cache",allEntries=true)
	public void saveOrUpdateDictionary(Dictionary dictionary) {
		dictionaryDao.save(dictionary);
	}	
	
	/**
	 * 根据外键查询所有子项内容
	 * @return
	 */
	@Override		
	public List<Dictionary> getDictionariesByTypeid(Long parentid) {
		//查询非逻辑删除并且外键为parentid的数据
		return dictionaryDao.findByExpressions(new String[]{"EQ_L_dictionaryType.id","EQ_S_isdel"}, new String[]{parentid+"","1"});
	}	
	
	/**
	 * 根据id批量逻辑删除数据字典{主表}信息
	 */
	@Override
	@CacheEvict(value="sys-cache",allEntries=true)
	public void delDictType(String ids) {
		String[] idsArr = ids.split(",");
		for(int i = 0; i < idsArr.length ; i++){
			DictionaryType dictionaryType = dictionaryTypeDao.get(Long.valueOf(idsArr[i].toString().trim()));
			dictionaryType.setIsdel("0");
			dictionaryTypeDao.update(dictionaryType);
		}
		
	}
	
	/**
	 * 根据id批量逻辑删除数据字典{子表}信息
	 */	
	@Override
	@CacheEvict(value="sys-cache",allEntries=true)
	public void delDict(String ids) {
		String[] idsArr = ids.split(",");
		for(int i = 0; i < idsArr.length ; i++){
			Dictionary dictionary = dictionaryDao.get(Long.valueOf(idsArr[i].toString().trim()));
			dictionary.setIsdel("0");
			dictionaryDao.update(dictionary);
		}		
	}	
	
	/**
	 * 根据id批量逻辑删除数据字典{映射表}信息
	 */	
	@Override
	@CacheEvict(value="sys-cache",allEntries=true)
	public void delMapping(String ids) {
		String[] idsArr = ids.split(",");
		for(int i = 0; i < idsArr.length ; i++){
			DictionaryMapping dictionaryMapping = dictionaryMappingDao.get(Long.valueOf(idsArr[i].toString().trim()));
			dictionaryMapping.setStatus("0");
			dictionaryMappingDao.update(dictionaryMapping);
		}			
	}	
	/**
	 * 根据外键查询映射表信息(非分页) 
	 */
	@Override
	public List<DictionaryMapping> getMappingByDictId(Long parentid) {
		//查询非逻辑删除并且外键为parentid的数据
		return dictionaryMappingDao.findByExpressions(new String[]{"EQ_L_dictionary.id","EQ_S_status"}, new String[]{parentid+"","1"});
	}	
	/**
	 * 保存或更新映射表信息
	 */
	@Override
	@CacheEvict(value="sys-cache",allEntries=true)
	public void saveOrUpdateMapping(DictionaryMapping dictionaryMapping) {
		dictionaryMappingDao.save(dictionaryMapping);
	}	
	
	@Override
	@Cacheable(value="sys-cache")
	public List<Dictionary> findDictionaryByParentCode(String parentCode) {
		String hql = " from Dictionary T where T.dictionaryType.code = ? and T.dictionaryType.isdel = ? and T.isdel = ? ";
		List<Dictionary> list =  dictionaryDao.findByQuery(hql, parentCode , "1" , "1");
		return list;
	}	
	
    @Autowired
	public void setDictionaryTypeDao(DictionaryTypeDao dictionaryTypeDao) {
		this.dictionaryTypeDao = dictionaryTypeDao;
		this.dao=dictionaryTypeDao;
	}

	@Override
	public String getName(String typeCode, String key) {
		String name="未知";
		List<Dictionary> list=this.getDictionariesByCode(typeCode);
		for(Dictionary dictionary:list){
			if(dictionary.getValue().equals(key)){
				name=dictionary.getName();
				break;
			}
		}
		return name;
	}
}
