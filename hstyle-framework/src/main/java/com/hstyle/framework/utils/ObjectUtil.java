package com.hstyle.framework.utils;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.PropertyUtils;

public class ObjectUtil {

	/**
	 * 
	* @Title: validPropertyValue
	* @Description: TODO(判断连个对象中的属性是否相等，父类不做处理，只处理属性转换为字符串无变化的数据)
	* @param @param obj
	* @param @param con
	* @param @return
	* @param @throws Exception    设定文件
	* @return boolean    返回类型
	* @throws
	 */
	public static boolean validPropertyValue(Object obj,Object con)throws Exception{
		Field fieldArr[]=obj.getClass().getDeclaredFields();
		boolean isEqual=true;
		for(Field child:fieldArr){
			try
			{
				Object value=PropertyUtils.getProperty(obj, child.getName());
				Object valueCon=PropertyUtils.getProperty(con, child.getName());
				if(valueCon==null){
					continue;
				}
				if(!value.toString().equals(valueCon.toString())){
					return false;
				}
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		return isEqual;
	}
}
