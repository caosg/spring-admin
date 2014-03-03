/**
 * 
 */
package com.hstyle.admin.dao.hb;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hstyle.admin.dao.OperationLogDao;
import com.hstyle.admin.model.OperationLog;
import com.hstyle.framework.dao.core.hibernate.AdvancedHibernateDao;

/**
 * @author Administrator
 *
 */
@Repository
public class OperationLogDaoHb extends AdvancedHibernateDao<OperationLog, Long> implements OperationLogDao {

	@Override
	public Map<Integer, List> statSysLog(final String type) {
		String procSqlName="{ call proc_sys_stat(?) }";
		return this.callProc(procSqlName, type);
	}

	@Override
	public Map<Integer, List> statSysOperationRank(int limit) {
		String procSqlName="{ call proc_stat_operation_rank(?) }";
		return this.callProc(procSqlName, limit);
	}

	@Override
	public Map<Integer, List> statHits(String type) {
		String procSqlName="{ call proc_stat_hits(?) }";
		return this.callProc(procSqlName, type);
	}
   
}
