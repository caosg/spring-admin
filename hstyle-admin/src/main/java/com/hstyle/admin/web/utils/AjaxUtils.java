package com.hstyle.admin.web.utils;

import org.springframework.web.context.request.WebRequest;

/**
 * 判断请求是否是Ajax方式请求
 * @author Administrator
 *
 */
public class AjaxUtils {

	public static boolean isAjaxRequest(WebRequest webRequest) {
		String requestedWith = webRequest.getHeader("X-Requested-With");
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}

	public static boolean isAjaxUploadRequest(WebRequest webRequest) {
		return webRequest.getParameter("ajaxUpload") != null;
	}
	
	private AjaxUtils() {}

}
