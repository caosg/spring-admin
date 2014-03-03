package com.hstyle.admin.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.hstyle.admin.service.AppConfig;
import com.hstyle.admin.service.ModulePropertyService;
@Service
public class AppConfigImp implements AppConfig {
	@Autowired
    private ModulePropertyService modulePropertyService;
	@Override
	@Cacheable(value="sys-cache")
	public String getStringProperty(String propetyCode) {
		return getStringPropertyByModel("SYS",propetyCode);
	}

	@Override
	@Cacheable(value="sys-cache")
	public String getStringPropertyByModel(String busicode, String propertyCode) {
		try {
			return modulePropertyService.getStringPropertyByModel(busicode, propertyCode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Cacheable(value="sys-cache")	
	public boolean getBooleanProperty(String propetyCode) {
		return getBooleanPropertyByModel("SYS",propetyCode);
	}

	@Override
	@Cacheable(value="sys-cache")
	public boolean getBooleanPropertyByModel(String modelCode,
			String propertyCode) {
		String value= modulePropertyService.getStringPropertyByModel(modelCode, propertyCode);
		if(value.equals("Y"))
			return true;
		return false;
	}

}
