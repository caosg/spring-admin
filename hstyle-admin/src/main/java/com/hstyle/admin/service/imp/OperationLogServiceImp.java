/**
 * 
 */
package com.hstyle.admin.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstyle.admin.dao.OperationLogDao;
import com.hstyle.admin.model.OperationLog;
import com.hstyle.admin.service.OperationLogService;
import com.hstyle.framework.service.DefaultServiceImp;

/**
 * @author Administrator
 *
 */
@Service("operationLogService")
public class OperationLogServiceImp extends DefaultServiceImp<OperationLog, Long> implements OperationLogService {
   
	
	private OperationLogDao operationLogDao;
	@Autowired
	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
		dao = operationLogDao;
	}
	@Override
	public Map<Integer, List> statSysLog(String type) {
		// TODO Auto-generated method stub
		return operationLogDao.statSysLog(type);
	}
	@Override
	public List statSysOperationRank(int limit) {
		List result=null;
		Map<Integer, List> map=operationLogDao.statSysOperationRank(limit);
		if(map!=null)
			result= map.get(0);
		return result;
	}
	@Override
	public List statHits(String timeType) {
		List result=null;
		Map<Integer, List> map=operationLogDao.statHits(timeType);
		if(map!=null)
			result= map.get(0);
		return result;
	}
	
}
