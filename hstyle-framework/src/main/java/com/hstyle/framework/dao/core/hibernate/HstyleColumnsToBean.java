package com.hstyle.framework.dao.core.hibernate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.HibernateException;
import org.hibernate.PropertyNotFoundException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

/**
 * 
 * @author hsb
 * @category:基本框架类
 * @todo:将sql查询语句中查询的列转化成对象
 *
 */
public class HstyleColumnsToBean implements ResultTransformer {
	 private static final long serialVersionUID = -5199190581393587893L;  
     private final Class resultClass;  
     private Setter[] setters;
     private PropertyAccessor propertyAccessor;
     public HstyleColumnsToBean(Class resultClass) {  
         if (resultClass == null) throw new IllegalArgumentException("resultClass cannot be null");  
         this.resultClass = resultClass;  
         propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[]{PropertyAccessorFactory.getPropertyAccessor(resultClass, null), PropertyAccessorFactory.getPropertyAccessor("field")});  
     }  
     public Object transformTuple(Object[] tuple, String[] aliases) {  
         Object result;  
         try {  
             if (setters == null) {  
                 setters = new Setter[aliases.length];  
                 for (int i = 0; i < aliases.length; i++) {  
                     String alias = aliases[i];
                     if (alias != null) {  
                         try {
                        	 if(alias.indexOf("_")!=-1){
                        		 String[] classProperty=alias.split("_");
                        		 setters[i] = propertyAccessor.getSetter(getPropertyClass(classProperty[0]), classProperty[1]);
                        	 }else
                        	 {
                        		 setters[i] = propertyAccessor.getSetter(resultClass, alias);
                        	 }
                         } catch (PropertyNotFoundException e) {  
                             continue;  
                         }  
                     }  
                 }  
             }  
             result = resultClass.newInstance();  
             for (int i = 0; i < aliases.length; i++) {  
                 if (setters[i] != null) {
                	 if(aliases[i].indexOf("_")!=-1){
                		 String[] classProperty=aliases[i].split("_");
                		 setters[i].set(getPropertyPojoValue(classProperty[0],result), tuple[i], null);
                	 }else
                	 {
                		 setters[i].set(result, tuple[i], null);
                	 }
                 }  
             }  
         } catch (InstantiationException e) {  
             throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());  
         } catch (IllegalAccessException e) {  
             throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());  
         }  
         return result;  
     }
     /**
      * 获取属性的类
      * @param alias
      * @return
      */
     public Class getPropertyClass(String alias){
    	 try
    	 {
	    	Field field = resultClass.getDeclaredField(alias);
	 		if(field==null){
	 			throw new RuntimeException("实体"+resultClass.getName()+"不含任何属性"+alias);
	 		}
	 		return field.getType();
	 		
    	 }catch(NoSuchFieldException e){
    		 throw new RuntimeException("实体"+resultClass.getName()+"不含任何属性"+alias);
    	 }
     }
     /**
      * 获取属性对象的实例
      * @param alias
      * @param instance
      * @return
      */
     public Object getPropertyPojoValue(String alias,Object instance){
    	 try
    	 {
	    	Field field = resultClass.getDeclaredField(alias);
	 		if(field==null){
	 			throw new RuntimeException("实体"+resultClass.getName()+"不含任何属性"+alias);
	 		}
	 		field.setAccessible(true);
	 		return field.get(instance);
	 		
    	 }catch(NoSuchFieldException e){
    		 throw new RuntimeException("实体"+resultClass.getName()+"不含任何属性"+alias);
    	 } catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
    		 throw new RuntimeException("实体"+resultClass.getName()+"不含任何属性"+alias);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("实体"+resultClass.getName()+"不含任何属性"+alias);
		}
     }
     /** 
      * Converts the specified 'XXX_YYY_ZZZ'-like column name to its 
      * 'xxxYyyZzz'-like Java property name. 
      * 
      * @param columnName the column name 
      * @return the Java property name 
      */  
     public String convertColumnToProperty(String columnName) {  
         columnName = columnName.toLowerCase();  
         StringBuffer buff = new StringBuffer(columnName.length());  
         StringTokenizer st = new StringTokenizer(columnName, "_");  
         while (st.hasMoreTokens()) {  
            buff.append(StringUtils.capitalize(st.nextToken()));  
         }  
         buff.setCharAt(0, Character.toLowerCase(buff.charAt(0)));  
         return buff.toString();  
     }  
     @SuppressWarnings({ "rawtypes", "unchecked" })  
  public List<T> transformList(List collection) {  
         return collection;  
     }  
}
