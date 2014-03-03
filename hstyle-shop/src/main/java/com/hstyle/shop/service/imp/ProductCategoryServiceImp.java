package com.hstyle.shop.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstyle.framework.service.DefaultServiceImp;
import com.hstyle.framework.utils.PropertiesLoader;
import com.hstyle.shop.dao.ProductCategoryDao;
import com.hstyle.shop.model.ProductCategory;
import com.hstyle.shop.service.ProductCategoryService;

@Service("productCategoryService")
public class ProductCategoryServiceImp extends DefaultServiceImp<ProductCategory, Long>
			implements ProductCategoryService {

	
	private ProductCategoryDao productCategoryDao;
	
	@Autowired
	public void setProductCategoryDao(ProductCategoryDao productCategoryDao) {
		this.productCategoryDao = productCategoryDao;
		dao=productCategoryDao;
	}

	/**
	 * 查询所有类目
	 */
	@Override
	public List<ProductCategory> getChildren(Long parentId) {
		//查询父ID为parentId并且非删除状态的
		return productCategoryDao.findByExpressions(new String[]{"EQ_L_parentId","EQ_S_status"}, new String[]{parentId+"","1"});
	}

	/**
	 * 保存/更新类目
	 */
	@Override
	public void saveProductCategory(
			ProductCategory productCategory) {
		int scales=PropertiesLoader.getInteger("productcategory.scale",3);
		String format = "%1$0"+scales+"d";
		ProductCategory parent = productCategoryDao.get(productCategory.getParentId());
		long totalChildNums=productCategoryDao.getChlidNum(productCategory.getParentId());
		if(parent.getId()==ProductCategory.ROOT_ID)
			    productCategory.setCode(String.format(format, 00));
			else
				productCategory.setCode(parent.getCode()+String.format(format, totalChildNums+1));
		  productCategoryDao.insert(productCategory);		
	}

	@Override
	public List<ProductCategory> getAllChildren(String code) {
		return productCategoryDao.findByExpressions(new String[]{"RLIKE_S_code","NE_S_code","EQ_S_status"}, new String[]{code,code,"1"});
	}

}
