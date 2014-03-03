package com.hstyle.admin.service.imp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hstyle.admin.service.AppNoCreator;
import com.hstyle.admin.service.Nextor;



@Component
public class AppNoCreatorImp implements AppNoCreator {
	private static Date date = new Date(); 
	@Autowired
	@Qualifier("localNextor")
	private Nextor nextor;
	public  String next(String prefix){
		long next=nextor.next(prefix);
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$06d", date, next);
		return prefix+str;
	}
}
