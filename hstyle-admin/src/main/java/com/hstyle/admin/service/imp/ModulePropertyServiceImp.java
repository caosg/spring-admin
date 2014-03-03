package com.hstyle.admin.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.hstyle.admin.dao.ModulePropertyDao;
import com.hstyle.admin.model.ModuleProperty;
import com.hstyle.admin.model.Property;
import com.hstyle.admin.service.ModulePropertyService;
import com.hstyle.common.utils.HStringUtil;
import com.hstyle.framework.dao.core.PropertyFilter;
import com.hstyle.framework.dao.core.PropertyType;
import com.hstyle.framework.exception.ApplicationException;
import com.hstyle.framework.mapper.BeanMapper;
import com.hstyle.framework.mapper.JsonMapper;
import com.hstyle.framework.service.DefaultServiceImp;
@Service("modulePropertyService")
public class ModulePropertyServiceImp extends DefaultServiceImp<ModuleProperty, Long> implements ModulePropertyService{
	private ModulePropertyDao modulePropertyDao;
	
	
	public ModulePropertyDao getModulePropertyDao() {
		return modulePropertyDao;
	}
	@Autowired
	public void setModulePropertyDao(ModulePropertyDao modulePropertyDao) {
		this.modulePropertyDao = modulePropertyDao;
		dao=modulePropertyDao;
	}
	
	@Override
	public List<ModuleProperty> getPropertyByModule(String code) {
		return modulePropertyDao.findByExpressions(new String[]{"EQ_S_module","EQ_S_busicode"}, new String[]{code,"GLOBAL"},ModuleProperty.class);
	}

	@Override
	public List<ModuleProperty> getPropertyByCode(String code,String modCode) {
		return modulePropertyDao.findByExpressions(new String[]{"EQ_S_code","EQ_S_module","EQ_S_busicode"}, new String[]{code,modCode,"GLOBAL"},ModuleProperty.class);
	}

	@Override
	public List<ModuleProperty> getPropertyByName(String name,String modCode) {
		return modulePropertyDao.findByExpressions(new String[]{"EQ_S_name","EQ_S_module","EQ_S_busicode"}, new String[]{name,modCode,"GLOBAL"},ModuleProperty.class);
	}




	@Override
	@CacheEvict(value="sys-cache",allEntries=true)
	public void updateConfig(String properties,String busicode,String moduleType ) throws ApplicationException {
		JsonMapper jsonMapper=JsonMapper.nonDefaultMapper();
		JavaType beanListType = jsonMapper.createCollectionType(List.class, Property.class);
		List<Property> propertieList=jsonMapper.fromJson(properties, beanListType);//转换属性项
		for(Property property:propertieList){
			Long id=property.getId();
			if(id ==null){
				throw new ApplicationException("ID为空，请重新试一下");  
			}
			ModuleProperty moudle=modulePropertyDao.get(ModuleProperty.class,id);
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			PropertyFilter moduleTypeFilter = new PropertyFilter("EQ", PropertyType.S, new String[] {"module"}, moudle.getModule());
			filters.add(moduleTypeFilter);
			PropertyFilter busiFilter = new PropertyFilter("EQ", PropertyType.S, new String[] {"busicode"}, busicode);
			filters.add(busiFilter);
			PropertyFilter modulecodeFilter = new PropertyFilter("EQ", PropertyType.S, new String[] {"code"}, moudle.getCode());
			filters.add(modulecodeFilter);
			List itemlist=modulePropertyDao.findFilters(filters, ModuleProperty.class);//查询子表信息
			if(itemlist!=null&&itemlist.size()>0){
				ModuleProperty item=(ModuleProperty) itemlist.get(0);
				item.setValue(property.getValue());
				modulePropertyDao.update(item);
			}else{
				ModuleProperty item=new ModuleProperty();
				item.setCode(moudle.getCode());
				item.setDefaultValue(moudle.getDefaultValue());
				item.setModule(moudle.getModule());
				item.setOptions(moudle.getOptions());
				item.setOrderNo(moudle.getOrderNo());
				item.setName(moudle.getName());
				item.setType(moudle.getType());
				item.setModuleType(moudle.getModule());
				item.setBusicode(busicode);
				item.setValue(property.getValue());
				item.setModuleType(moduleType);
				modulePropertyDao.save(item);
			}
			
		}
		}


	@Override
	public List<ModuleProperty> getPropertyByOrderNo(String orderNo,
			String modCode) {
		// TODO Auto-generated method stub
		return modulePropertyDao.findByExpressions(new String[]{"EQ_L_orderNo","EQ_S_module","EQ_S_busicode"}, new String[]{orderNo,modCode,"GLOBAL"},ModuleProperty.class);
	}



	@Override
	public long getLongValueByCode(String code, String modCode)  throws Exception{
		try {
			if (HStringUtil.isBlank(code)||HStringUtil.isBlank(modCode)) {
				throw new RuntimeException("参数有空值，请确认传递参数！");
			}
			String hql = "select M.value from ModuleProperty M where M.code = ? and M.module = ?";
			return Long.parseLong((String) modulePropertyDao.findUniqueByQuery(hql, new Object[]{code,modCode}));
		} catch (Exception e) {
			throw e;
		}
	}



	@Override
	public String getStringValueByCode(String code, String modCode) throws Exception{
		try {
			if (HStringUtil.isBlank(code)||HStringUtil.isBlank(modCode)) {
				throw new RuntimeException("参数有空值，请确认传递参数！");
			}
			String hql = "select M.value from ModuleProperty M where M.code = ? and M.module = ?";
			return modulePropertyDao.findUniqueByQuery(hql, new Object[]{code,modCode});
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<String> getListValueByCode(String code, String modCode) throws Exception{
		try {
			if (HStringUtil.isBlank(code)||HStringUtil.isBlank(modCode)) {
				throw new RuntimeException("参数有空值，请确认传递参数！");
			}
			String hql = "select M.value from ModuleProperty M where M.code = ? and M.module = ?";
			String str =  modulePropertyDao.findUniqueByQuery(hql, new Object[]{code,modCode});
			String strs[] = str.split(",");
			List<String> values = Arrays.asList(strs);
			return values;
		} catch (Exception e) {
			throw e;
		}
	}



	@Override
	public Date getDateValueByCode(String code, String modCode)  throws Exception{
		try {
			if (HStringUtil.isBlank(code)||HStringUtil.isBlank(modCode)) {
				throw new RuntimeException("参数有空值，请确认传递参数！");
			}
			String hql = "select M.value from ModuleProperty M where M.code = ? and M.module = ?";
			String str =  modulePropertyDao.findUniqueByQuery(hql, new Object[]{code,modCode});
			Date value = new SimpleDateFormat("yyyy-mm-dd").parse(str);
			return value;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	@CacheEvict(value="sys-cache",allEntries=true)
	public void editModuleProperty(ModuleProperty property, ModuleProperty mp) {
		// TODO Auto-generated method stub
		BeanMapper.copy(property, mp);
		mp.setId(property.getId());
		mp.setBusicode("GLOBAL");
		ModuleProperty moProperty=new ModuleProperty();
		moProperty.setCode(property.getCode());
		moProperty.setModule(property.getModule());
		List<ModuleProperty> list=modulePropertyDao.findByEntity(moProperty);
		if(list!=null&&list.size()>0){
			for(ModuleProperty mopro:list){
				if(!mopro.getBusicode().equals("GLOBAL")){
					mopro.setDefaultValue(property.getDefaultValue());
					mopro.setName(property.getName());
					mopro.setOptions(property.getOptions());
					mopro.setOrderNo(property.getOrderNo());
					mopro.setRemark(property.getRemark());
					mopro.setType(property.getType());
					modulePropertyDao.update(mopro);
				}
			}
		}
		
		modulePropertyDao.update(mp);
	}
	@Override
	@CacheEvict(value="sys-cache",allEntries=true)
	public void deleteModuleProperty(ModuleProperty mp) {
		// TODO Auto-generated method stub
		ModuleProperty moProperty=new ModuleProperty();
		moProperty.setCode(mp.getCode());
		moProperty.setModule(mp.getModule());
		List<ModuleProperty> list=modulePropertyDao.findByEntity(moProperty);
		if(list!=null&&list.size()>0){
			for(ModuleProperty mopro:list){
				modulePropertyDao.delete(mopro.getId());
			}
		}
		modulePropertyDao.delete(mp.getId());
		
	}
	@Override
	@Cacheable(value="sys-cache")
	public List<ModuleProperty> listSysConfig(String module,String busicode) {
		ModuleProperty global=new ModuleProperty();
		global.setModule(module);
		global.setBusicode("GLOBAL");
		List<ModuleProperty> globallist=modulePropertyDao.findByEntity(global);
		ModuleProperty moProperty=new ModuleProperty();
		moProperty.setBusicode(busicode);
		moProperty.setModule(module);
		List<ModuleProperty> list=modulePropertyDao.findByEntity(moProperty);
		Map<String ,ModuleProperty> map=new HashMap<String ,ModuleProperty>();
		if(list.size()!=globallist.size()){//不相等的话
			for(ModuleProperty pro:globallist){
				map.put(pro.getCode(), pro);
			}
			for(ModuleProperty pro:list){
				map.remove(pro.getCode());
			}
		}
		Set<String> key = map.keySet();
        for (Iterator<String> it = key.iterator(); it.hasNext();) {//新增加
            String code= (String) it.next();
            ModuleProperty propp=map.get(code);
            list.add(propp);
           
        }
        return list;
	}
	
	@Override
	@Cacheable(value="sys-cache")
	public String getStringPropertyByModel(String modelCode , String propertycode) {
		String values="";
		String proarr[]=propertycode.split("\\.");
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		PropertyFilter moduleTypeFilter = new PropertyFilter("EQ", PropertyType.S, new String[] {"module"}, proarr[0].toString());
		filters.add(moduleTypeFilter);
		PropertyFilter busiFilter = new PropertyFilter("EQ", PropertyType.S, new String[] {"busicode"}, modelCode);
		filters.add(busiFilter);
		PropertyFilter modulecodeFilter = new PropertyFilter("EQ", PropertyType.S, new String[] {"code"}, proarr[1].toString());
		filters.add(modulecodeFilter);
		List itemlist=modulePropertyDao.findFilters(filters, ModuleProperty.class);//查询子表信息
		if(itemlist!=null&&itemlist.size()>0){
			ModuleProperty property=(ModuleProperty) itemlist.get(0);
			values=property.getValue();
		}
		return values;
		
	}
	
	






	
	
}
