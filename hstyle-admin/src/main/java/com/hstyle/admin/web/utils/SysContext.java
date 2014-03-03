/**
 * 
 */
package com.hstyle.admin.web.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;

/**
 * 系统应用上下文
 * @author Administrator
 *
 */
public class SysContext {
	public static Map<String,HttpSession> SESSION_MAP = new HashMap<String,HttpSession>();//用来索引所有session	    
	public static AtomicInteger CURRENT_LOGIN_COUNT = new AtomicInteger(0);//当前登录用户总数，并发原子操作
	public static AtomicInteger TOTAL_HISTORY_COUNT = new AtomicInteger(0);//历史访客总数
	public static AtomicInteger MAX_ONLINE_COUNT =  new AtomicInteger(0);//最大在线访客数量
	public static String START_DATE ;//服务器启动时间
	public static String MAX_ONLINE_COUNT_DATE;//达到最大访客量的日期
	public static String VM_NAME;//系统jvm信息
	public static String VM_VERSION;//系统jvm版本
	public static String VM_VENDOR;
	public static String APP_SERVER;//应用服务器信息
	public static String APP_OS;//服务器操作系统信息
	public static Locale SYS_LOCAL=Locale.getDefault();//系统语言环境
	static{
		VM_NAME= System.getProperty("java.vm.name");
		VM_VERSION=System.getProperty("java.version");
		VM_VENDOR=System.getProperty("java.vendor");
        if (VM_NAME == null) {
        	VM_NAME = "";
        }
        APP_OS=System.getProperty("os.name")+"/"+System.getProperty("os.arch") ;
        
	}
	public static String getServerInfo(){
		StringBuilder stringBuilder=new StringBuilder("");
		stringBuilder.append("服务器启动时间:").append(START_DATE).append("\n")
		             .append("系统jvm:").append(VM_NAME).append("\n")
		             .append("系统jvm版本:").append(VM_VERSION).append("--").append(VM_VENDOR).append("\n")
		             .append("服务器信息:").append(APP_SERVER).append("\n")
		             .append("服务器操作系统:").append(APP_OS).append("\n")
		             .append("本地语言:").append(SYS_LOCAL);
		return stringBuilder.toString();
   }
}
