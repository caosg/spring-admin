/**
 * 
 */
package com.hstyle.admin.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hstyle.admin.model.OperationLog;
import com.hstyle.admin.service.OperationLogService;
import com.hstyle.framework.utils.SpringContextUtil;

/**
 * @author Administrator
 * 
 */
public class LogRecorderTask implements Runnable {
	private Logger log = LoggerFactory.getLogger(getClass());
	private int DEFAULT_NUM = 10;// 批量记录数量，减少数据库频繁写入
	private int WAIT_MILLIS = 5000;// 数量不足，等待时间；
	private OperationLogService operationLogService;
	private int batchNum;
	public LogRecorderTask(){
		this.batchNum=DEFAULT_NUM;
	}
    public LogRecorderTask(int batchNum){
    	this.batchNum=batchNum;
    }
	@Override
	public void run() {
		boolean done = false;
		try {
			while (!done) {
				int size = LogQueue.logqueue.size();
				if(this.batchNum==0)
					this.batchNum=DEFAULT_NUM;
				if (size >= this.batchNum) {log.info("LogRecorderTask is running ....");
					List<OperationLog> list = new ArrayList<OperationLog>();
					for (int i = 0; i < DEFAULT_NUM; i++) {
						OperationLog log = LogQueue.poll();
						if (log != null)
							list.add(log);
					}

					initLogSevice();
					operationLogService.addAll(list);
					log.info(".......save operation log to db ....");
					
				} else {

					//log.info("LogRecorderTask is sleeping ....");
					Thread.sleep(WAIT_MILLIS);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initLogSevice() {
		if (operationLogService == null)
			operationLogService = (OperationLogService) SpringContextUtil.getBean("operationLogService");
	}

}
