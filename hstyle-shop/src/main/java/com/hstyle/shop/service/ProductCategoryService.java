/**
 * 
 */
package com.hstyle.shop.service;

import java.util.List;

import com.hstyle.framework.service.GenericService;
import com.hstyle.shop.model.ProductCategory;

/**
 * @author jiaoyuqiang
 * 
 */
public interface ProductCategoryService extends GenericService<ProductCategory, Long> {
	/**
	 * 根据父类目代码查找子类目
	 * 1:分页
	 * @param parentId
	 * @return
	 */
	List<ProductCategory> getChildren(Long parentId);
	
	/**
	 * 保存/更新类目
	 * @param productCategory
	 */
	void saveProductCategory(ProductCategory productCategory);
	
	/**
	 * 根据父类代码查找所有子类代码 
	 * 1:非分页
	 * @param code
	 * @return
	 */
	List<ProductCategory> getAllChildren(String code);
}
