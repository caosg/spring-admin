package com.hstyle.framework.dao.core.hibernate.property.impl.restriction;

import com.hstyle.framework.dao.core.hibernate.property.impl.PropertyValueRestrictionSuper;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * 左模糊约束 ( from object o where o.value like '%?') RestrictionName:LLIKE
 * <p>
 * 表达式:LLIKE_属性类型_属性名称[_OR_属性名称...]
 * </p>
 * 
 * @author 
 *
 */
public class LLikeRestriction extends PropertyValueRestrictionSuper{

	public final static String RestrictionName = "LLIKE";
	
	
	public String getRestrictionName() {
		return RestrictionName;
	}

	
	public Criterion build(String propertyName, Object value) {
		
		return Restrictions.like(propertyName, value.toString(), MatchMode.END);
	}

}

