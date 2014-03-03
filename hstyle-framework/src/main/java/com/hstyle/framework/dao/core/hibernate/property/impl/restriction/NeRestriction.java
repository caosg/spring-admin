package com.hstyle.framework.dao.core.hibernate.property.impl.restriction;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * 不等于约束 ( from object o where o.value <> ?) RestrictionName:NE
 * <p>
 * 表达式:NE_属性类型_属性名称[_OR_属性名称...]
 * </p>
 * 
 * @author
 *
 */
public class NeRestriction extends EqRestriction{
	
	public final static String RestrictionName = "NE";


	
	public String getRestrictionName() {
		
		return RestrictionName;
	}


	
	public Criterion build(String propertyName, Object value) {
		
		if (value == null) {
			return Restrictions.isNotNull(propertyName);
		} else {
			return Restrictions.ne(propertyName, value);
		}
	}

	
}
