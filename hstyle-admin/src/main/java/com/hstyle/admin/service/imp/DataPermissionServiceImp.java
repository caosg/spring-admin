package com.hstyle.admin.service.imp;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstyle.admin.dao.DataPermissionDao;
import com.hstyle.admin.model.DataPermission;
import com.hstyle.admin.model.DataTarget;
import com.hstyle.admin.service.DataPermissionService;
import com.hstyle.framework.service.DefaultServiceImp;
@Service
public class DataPermissionServiceImp extends DefaultServiceImp<DataPermission, Long>
		implements DataPermissionService {
	
	private DataPermissionDao dataPermissionDao;
	@Autowired
	public void setDataPermissionDao(DataPermissionDao dataPermissionDao){
		this.dataPermissionDao=dataPermissionDao;
		this.dao=dataPermissionDao;
	}

	@Override
	public Collection<DataPermission> getChildren(Long parentId) {
		// TODO Auto-generated method stub
		return dataPermissionDao.findByExpressions(new String[]{"EQ_L_parent.id","NE_S_resource"}, new String[]{parentId+"",null},"sort_asc");
	}

	@Override
	public List<DataPermission> findPermissionRules(Long typeId) {
		// TODO Auto-generated method stub
		return dataPermissionDao.findByExpressions(new String[]{"EQ_L_parent.id"}, new String[]{typeId+""},"sort_asc");
	}

	@Override
	public void saveTarget(DataTarget target) {
		dataPermissionDao.saveObject(target);
	}

	@Override
	public void removeTarget(List<Long> ids) {
		for(Long id:ids){
			dataPermissionDao.deleteObject(DataTarget.class, id);
		}
		
	}

}
