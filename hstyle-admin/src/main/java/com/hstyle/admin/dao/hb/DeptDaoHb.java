/**
 * 
 */
package com.hstyle.admin.dao.hb;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.hstyle.admin.dao.DeptDao;
import com.hstyle.admin.model.Dept;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;
import com.hstyle.framework.utils.PropertiesLoader;

/**
 * @author Administrator
 *
 */
@Repository
public class DeptDaoHb extends AdvancedHibernateDao<Dept, Long> implements DeptDao {

	@Override
	public long getChlidNum(Long parentId) {
		// TODO Auto-generated method stub
		String hql="from Dept d where d.parentId=? order by d.code desc";
		List<Dept> childDepts = this.findByQuery(hql, parentId);
		if(childDepts!=null&&childDepts.size()>0){
			Dept maxDept = childDepts.get(0);
			int scales=PropertiesLoader.getInteger("dept.scale",3);
			String maxCode = StringUtils.substring(maxDept.getCode(), -scales);
			return Long.parseLong(maxCode);
		}else
			return 0;
		//return super.countHqlResult(hql, parentId);
	}

}
