/**
 * 
 */
package com.hstyle.admin.dao;

import java.util.List;
import java.util.Map;

import com.hstyle.admin.model.OperationLog;
import com.hstyle.framework.dao.core.AdvancedDao;

/**
 * 操作日志dao
 * @author Administrator
 *
 */
public interface OperationLogDao extends AdvancedDao<OperationLog, Long> {
	/**
	 * 独立用户数。session数访问次数统计
	 * @param type hour:小时统计 day：按天统计 week：按周统计
	 * @return
	 */
	public Map<Integer, List> statSysLog(String type);
	
	/**
	 * 总点击量统计
	 * @param type hour:小时统计 day：按天统计 week：按周统计
	 * @return
	 */
	public Map<Integer, List> statHits(String type);
	
	/**
     * 按点击次数排行功能操作
     * @param limit 查询条数  0:所有  
     * @return
     */
    public Map<Integer, List> statSysOperationRank(int limit); 
}
