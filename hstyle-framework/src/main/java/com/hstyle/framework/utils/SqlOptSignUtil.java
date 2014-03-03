package com.hstyle.framework.utils;

import com.hstyle.common.utils.HStringUtil;

public class SqlOptSignUtil {
	public static final String GT=">";
	public static final String GTE=">=";
	public static final String LT="<";
	public static final String LTE="<=";
	public static final String EQ="=";
	public static final String LIKE="like";
	public static final String BETWEEN="between";
	public static final String IN="in";
	public static final String UNEQ="!=";
	public static final String UNLIKE="not like";
	public static final String UNIN="not in";
	
	public static final String TYPE_STR="string";
	public static final String TYPE_DBL="double";
	public static final String TYPE_LNG="long";
	public static final String TYPE_DATE="date";
	public static final String TYPE_INT="int";
	public static final String TYPE_INTEGER="integer";
	
	/**
	 * @category 转为对应类型的数值
	 * @param type
	 * @param value
	 * @param format
	 * @return
	 */
	public static Object getRealValue(String type,String value,String format){
		if(TYPE_DBL.equals(type.toLowerCase())){
			if(value==null || "".equals(value))return 0D;
			return Double.valueOf(value);
		}else if(TYPE_LNG.equals(type.toLowerCase())){
			if(value==null || "".equals(value))return 0L;
			return Long.valueOf(value);
		}else if(TYPE_LNG.equals(type.toLowerCase())){
			if(value==null || "".equals(value))return 0L;
			return Long.valueOf(value);
		}else if(TYPE_INT.equals(type.toLowerCase())){
			if(value==null || "".equals(value))return 0;
			return Integer.valueOf(value);
		}else if(TYPE_INTEGER.equals(type.toLowerCase())){
			if(value==null || "".equals(value))return 0;
			return Integer.valueOf(value);
		}else if(TYPE_DATE.equals(type.toLowerCase())){
			if(value==null || "".equals(value))return null;
			return HStringUtil.getDateFstr(value, format);
		}
		return value;
	}
}
