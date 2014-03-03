/**
 * 
 */
package com.hstyle.admin.web.utils;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.hstyle.admin.model.OperationLog;

/**
 * 非阻塞异步队列
 * @author Administrator
 *
 */
public class LogQueue {
   public static ConcurrentLinkedQueue<OperationLog> logqueue=new ConcurrentLinkedQueue<OperationLog>();
   public static void add(OperationLog operationLog){
	   
	   logqueue.offer(operationLog);
   }
   public static OperationLog poll(){
	   return logqueue.poll();
   }
}
