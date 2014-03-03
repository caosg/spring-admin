package com.hstyle.admin.web.security;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 验证码异常
 * @author Administrator
 *
 */
public class IncorrectCaptchaException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8443765870774322603L;

	public IncorrectCaptchaException() {
		super();
	}

	public IncorrectCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCaptchaException(String message) {
		super(message);
	}

	public IncorrectCaptchaException(Throwable cause) {
		super(cause);
	}
}