package com.hstyle.admin.web.controller;

/**
 * 封装返回页面的json格式数据，返回json数据如下,msg可以是各种对象的json数据 :/br
 * 如：{"success":true,"data":"xxx"}
 * or {"success":true,"data":jsonStr}
 * @author Administrator
 *
 */
public class Msg {

	private boolean success;
	private Object data="执行成功";
	public Msg() {
		// TODO Auto-generated constructor stub
	}
	
	public Msg(boolean success,Object result){
		this.success=success;
		if(result==null)
			this.setData("[]");
		else
		  this.setData(result);
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	

}
