/**
 * 
 */
package com.hstyle.framework.exception;

/**
 * 系统运行时异常，非业务异常
 * @author Administrator
 *
 */
public class SysRunTimeException extends RuntimeException {

	public SysRunTimeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SysRunTimeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SysRunTimeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public SysRunTimeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -3409159114369775740L;

}
