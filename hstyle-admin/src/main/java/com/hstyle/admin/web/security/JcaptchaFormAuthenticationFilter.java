/**
 * 
 */
package com.hstyle.admin.web.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

/**
 * shiro验证码登录扩展filter
 * @author Administrator
 *
 */
public class JcaptchaFormAuthenticationFilter extends FormAuthenticationFilter{

	public static final String DEFAULT_CAPTCHA_PARAM = "jcaptcha";
	
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private final static Logger logger=LoggerFactory.getLogger(JcaptchaFormAuthenticationFilter.class);
   
	@Override
	protected boolean executeLogin(ServletRequest request, 
	      ServletResponse response) throws Exception { 
	    String captcha=getCaptcha(request);
	    logger.info("-------------------------验证码:{}",captcha);
	    //验证码验证
	    boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse((HttpServletRequest)request, captcha);
	    if(!captchaPassed)
	    	return onLoginFailure(this.createToken(request, response), new IncorrectCaptchaException ("验证码错误！"), request, response); 
		return super.executeLogin(request, response);
	      
	} 

	/**
	 * @return the captchaParam
	 */
	public String getCaptchaParam() {
		return captchaParam;
	}

	/**
	 * @param captchaParam the captchaParam to set
	 */
	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}
	/**
	 * get the captcha
	 * @param request
	 * @return
	 */
	protected String getCaptcha(ServletRequest request) { 
	      return WebUtils.getCleanParam(request, getCaptchaParam()); 
	}

}
