package com.hstyle.framework.dao.core.hibernate.property.impl.restriction;

import com.hstyle.framework.dao.core.hibernate.property.impl.PropertyValueRestrictionSuper;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * 小于约束 ( from object o where o.value < ?) RestrictionName:LT
 * <p>
 * 表达式:LT_属性类型_属性名称[_OR_属性名称...]
 * </p>
 * 
 * @author
 *
 */
public class LtRestriction extends PropertyValueRestrictionSuper{

	public final static String RestrictionName = "LT";
	
	
	public String getRestrictionName() {
		return RestrictionName;
	}

	
	public Criterion build(String propertyName, Object value) {
		return Restrictions.lt(propertyName, value);
	}

}
