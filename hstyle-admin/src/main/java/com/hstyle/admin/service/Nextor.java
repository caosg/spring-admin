/**
 * 
 */
package com.hstyle.admin.service;

/**
 * 工作单号生成器
 * 
 * @author Administrator
 * 
 */
public interface Nextor {
	/**
	 * 生成下一单号
	 * 
	 * @param prefix
	 * @return
	 */
	long next(String prefix);
}
