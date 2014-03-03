/**
 * 
 */
package com.hstyle.admin.web.listener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hstyle.admin.web.utils.SysContext;

/**
 * @author Administrator
 * 
 */

public class SysSessionListener implements HttpSessionListener,HttpSessionAttributeListener{
	private Logger log = LoggerFactory.getLogger(getClass());
	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
		
		log.info("...........session created.............................." );
		// 新创建的session
		HttpSession session = (HttpSession) sessionEvent.getSession();
		// 保存session
		SysContext.SESSION_MAP.put(session.getId(), session);
		// 在线人数++
		SysContext.TOTAL_HISTORY_COUNT.incrementAndGet();
		DateFormat dFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 如果超过最大在线人数 更新 更新时间
		if (SysContext.SESSION_MAP.size() >= SysContext.MAX_ONLINE_COUNT
				.intValue()) {
			// 更新最大在线人数
			SysContext.MAX_ONLINE_COUNT.set(SysContext.SESSION_MAP.size());
					
			// 更新日期
			SysContext.MAX_ONLINE_COUNT_DATE = dFormat.format(new Date());
		}
		log.info("....sys start time:{}  current login users:{} total users:{} max online count:{}",SysContext.START_DATE,
				SysContext.CURRENT_LOGIN_COUNT.intValue(),SysContext.TOTAL_HISTORY_COUNT.intValue(),SysContext.MAX_ONLINE_COUNT.intValue());
	}

	// session销毁的时候调用
	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		// TODO Auto-generated method stub
		// 即将被销毁的session
		HttpSession session = sessionEvent.getSession();
		// 从map中将sesion的索引删除
		SysContext.SESSION_MAP.remove(session.getId());
		
		log.info("..... session destroyed ,cur users num:{}.......",SysContext.CURRENT_LOGIN_COUNT);
	}

	// 添加属性的时候被调用
	public void attributeAdded(HttpSessionBindingEvent event) {
		
		// 如果是user
		if (event.getName().equals("loginName")) {
			log.info("..... session attribute add.......");
			// 当前在线人数++
			SysContext.CURRENT_LOGIN_COUNT.incrementAndGet();
			// 得到session
			/*HttpSession session = event.getSession();
            //String repeatSessionId=null;
			// 查询该账户有没有在别的机器上登录,如果登陆则踢出
			for (HttpSession sess : SysContext.SESSION_MAP.values()) {

				if (event.getValue().equals(sess.getAttribute("loginName"))
						&& sess.getId() != session.getId()) {
					
					repeatSessionId=sess.getId();
					sess.invalidate();// 销毁session
				}
			}
			if(repeatSessionId!=null){
				SysContext.SESSION_MAP.remove(repeatSessionId);
				log.info("..... session id:{} was removed .......",repeatSessionId);
			}*/
		}
	}

	// 移除属性是被调用
	public void attributeRemoved(HttpSessionBindingEvent event) {
		if (event.getName().equals("loginName")) {
			
		     SysContext.CURRENT_LOGIN_COUNT.decrementAndGet();// 当前用户总数--
                     log.info("..... session attribute removed,cur user num:{}......",SysContext.CURRENT_LOGIN_COUNT);
		}
	}

	// 修改的时候调用
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}

}
