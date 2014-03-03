/**
 * 
 */
package com.hstyle.admin.service;

import java.util.List;
import java.util.Map;

import com.hstyle.admin.model.OperationLog;
import com.hstyle.framework.service.GenericService;

/**
 * @author Administrator
 * 
 */
public interface OperationLogService extends GenericService<OperationLog, Long> {

    /**
     * 系统访问统计
     * @param type hour：小时 day：天 week：周
     * @return
     */
    public Map<Integer, List> statSysLog(String type);
    
    /**
     * 按点击次数排行功能操作
     * @param limit 查询条数  0:所有  
     * @return
     */
    public List statSysOperationRank(int limit);
    
    /**
     * 操作时间总点击量统计 
     * @param timeType hour:小时  day:天  week:周
     * @return
     */
    public List statHits(String timeType);
}
