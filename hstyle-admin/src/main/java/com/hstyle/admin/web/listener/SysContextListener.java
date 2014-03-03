/**
 * 
 */
package com.hstyle.admin.web.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hstyle.admin.model.OperationLog;
import com.hstyle.admin.service.OperationLogService;
import com.hstyle.admin.web.utils.LogQueue;
import com.hstyle.admin.web.utils.LogRecorderTask;
import com.hstyle.admin.web.utils.SysContext;
import com.hstyle.framework.utils.SpringContextUtil;


/**
 * @author Administrator
 *
 */

public class SysContextListener implements ServletContextListener {
	private Logger log = LoggerFactory.getLogger(getClass());
	private OperationLogService operationLogService;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		DateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String batchString=sce.getServletContext().getInitParameter("batch-num");
		SysContext.START_DATE=dFormat.format(new Date());
		SysContext.APP_SERVER=sce.getServletContext().getServerInfo();
		//启动日志监听进程
		LogRecorderTask logRecorderTask=null;
		if(StringUtils.isEmpty(batchString))
			logRecorderTask=new LogRecorderTask();
		else
			logRecorderTask=new LogRecorderTask(Integer.parseInt(batchString));
		ExecutorService pool = Executors.newSingleThreadExecutor();		
		pool.submit(logRecorderTask);
		log.info("............starting logrecord task thread ...\n**********syscontext info:***********\n{}\n****************************",
				SysContext.getServerInfo());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		int size = LogQueue.logqueue.size();
		log.info("SysContextListener contextDestroyed is running ....");
		if (size >0) {
			
			List<OperationLog> list = new ArrayList<OperationLog>();
			for (int i = 0; i < size; i++) {
				OperationLog log = LogQueue.poll();
				if (log != null)
					list.add(log);
			}

			initLogSevice();
			operationLogService.addAll(list);
			log.info("......SysContextListener save operation log to db ....");
			
		}
		
	}
	private void initLogSevice() {
		if (operationLogService == null)
			operationLogService = (OperationLogService) SpringContextUtil.getBean("operationLogService");
	}

}
