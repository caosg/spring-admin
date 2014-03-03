/**
 * 
 */
package com.hstyle.admin.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hstyle.admin.common.State;
import com.hstyle.admin.dao.DeptDao;
import com.hstyle.admin.model.Dept;
import com.hstyle.admin.service.DeptService;
import com.hstyle.framework.service.DefaultServiceImp;
import com.hstyle.framework.utils.PropertiesLoader;

/**
 * @author Administrator
 *
 */
@Service
public class DeptServiceImp extends DefaultServiceImp<Dept, Long> implements
		DeptService {
    private DeptDao deptDao;

    @Autowired
	public void setDeptDao(DeptDao deptDao) {
		this.deptDao = deptDao;
		this.dao=deptDao;
	}
	@Override
	public List<Dept> getChildren(Long parentId) {

    	//只查询非删除状态的
		return deptDao.findByExpressions(new String[]{"EQ_L_parentId","NE_S_status"}, new String[]{parentId+"",State.Delete.getValue()});
	}
	
	@Override
	public void addDept(Dept dept) {
		//部门代码宽度,默认宽度3位
		int scales=PropertiesLoader.getInteger("dept.scale",3);
		String foramt="%1$0"+scales+"d";
		Dept parentDept=deptDao.get(dept.getParentId());
		Assert.notNull(parentDept, "paret dept must not be null!");
		long totalChildNums=deptDao.getChlidNum(dept.getParentId());
		//一级部门,不用拼上级code；code规则:一级001,002  二级001001,001002，002001.。。
		if(parentDept.getId()==Dept.ROOT_ID)
		   dept.setCode(String.format(foramt, totalChildNums+1));
		else
			dept.setCode(parentDept.getCode()+String.format(foramt, totalChildNums+1));
		deptDao.insert(dept);
	}
	@Override
	public List<Dept> getAllChildren(String parentDeptCode) {
		return deptDao.findByExpressions(new String[]{"RLIKE_S_code","NE_S_code","NE_S_status"}, new String[]{parentDeptCode,parentDeptCode,State.Delete.getValue()});
	}
	@Override
	public void editDept(Dept dept) {
		
		
	}
	 
}
