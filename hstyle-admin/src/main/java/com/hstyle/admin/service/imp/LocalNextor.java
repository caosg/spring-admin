package com.hstyle.admin.service.imp;

import org.springframework.stereotype.Component;

import com.hstyle.admin.service.Nextor;

/**
 * 本地单号简单生成，不支持分布式
 * @author Administrator
 *
 */
@Component("localNextor")
public class LocalNextor implements Nextor {
	private static int seq = 0;  
	private static final int ROTATION = 999999;
	@Override
	public synchronized long next(String prefix) {
		if (seq > ROTATION) seq = 0;
		return seq++;
	}

}
