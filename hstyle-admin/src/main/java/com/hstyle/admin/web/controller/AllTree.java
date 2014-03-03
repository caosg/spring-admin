package com.hstyle.admin.web.controller;

public class AllTree {
	private boolean success;
	private Object children;
	public AllTree(){};
	public AllTree(boolean success,Object children) {
		this.success=success;
		this.children=children;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * @return the children
	 */
	public Object getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(Object children) {
		this.children = children;
	}
}
