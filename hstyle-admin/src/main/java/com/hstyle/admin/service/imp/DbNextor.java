package com.hstyle.admin.service.imp;

import org.springframework.stereotype.Component;

import com.hstyle.admin.service.Nextor;

/**
 * 单号记录在数据库里，暂未实现
 * @author Administrator
 *
 */
@Component
public class DbNextor implements Nextor {

	@Override
	public long next(String prefix) {
		// TODO Auto-generated method stub
		return 0;
	}

}
