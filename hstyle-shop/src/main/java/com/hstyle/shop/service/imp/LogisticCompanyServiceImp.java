package com.hstyle.shop.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstyle.framework.service.DefaultServiceImp;
import com.hstyle.shop.dao.LogisticCompanyDao;
import com.hstyle.shop.model.LogisticCompany;
import com.hstyle.shop.service.LogisticCompanyService;
@Service("logisticCompanyService")
public class LogisticCompanyServiceImp extends DefaultServiceImp<LogisticCompany,Long> implements LogisticCompanyService{
	
	private LogisticCompanyDao logisticCompanyDao;
	
	@Autowired
	public void setLogisticCompanyDao(LogisticCompanyDao logisticCompanyDao) {
		this.logisticCompanyDao = logisticCompanyDao;
		this.dao = logisticCompanyDao;
	}
	
	
}
