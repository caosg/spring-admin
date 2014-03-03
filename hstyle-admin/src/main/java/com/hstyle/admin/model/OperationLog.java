/**
 * 操作日志model
 */
package com.hstyle.admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hstyle.framework.model.IdEntity;

/**
 * @author Administrator
 * 
 */
@Entity
@Table(name = "sys_operation_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OperationLog extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1933372404333453730L;
	
	private Long userId;//用户id
	
	private String sessionId;//sessionid
	
	private Long functionId;//模块id
	
	private Date visiteDate;//操作时间
	
	private int year;
	
	private int month;
	
	private int dayOfMonth;
	
	private int week;
	
	private String remark;//备注

	@Column(name="user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name="session_id")
	public String getSessionId() {
		return sessionId;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	@Column(name="function_id")
	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	@Column(name="visite_date")
	public Date getVisiteDate() {
		return visiteDate;
	}

	public void setVisiteDate(Date visiteDate) {
		this.visiteDate = visiteDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	/**
	 * @return the dayOfMonth
	 */
	@Column(name="day_of_month")
	public int getDayOfMonth() {
		return dayOfMonth;
	}

	/**
	 * @param dayOfMonth the dayOfMonth to set
	 */
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	
}
