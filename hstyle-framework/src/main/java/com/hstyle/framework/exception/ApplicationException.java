/**
 * 
 */
package com.hstyle.framework.exception;

/**
 * 业务异常，执行业务逻辑，抛出业务检查异常
 * @author Administrator
 *
 */
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 8433724960265870538L;

	/**
	 * 
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * @param message
	 */
	public ApplicationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
