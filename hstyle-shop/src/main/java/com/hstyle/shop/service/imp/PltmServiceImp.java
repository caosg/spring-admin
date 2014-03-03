/**
 * 
 */
package com.hstyle.shop.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hstyle.framework.model.IdEntity;
import com.hstyle.framework.service.DefaultServiceImp;
import com.hstyle.shop.dao.PltmDao;
import com.hstyle.shop.service.PltmService;

/**
 * 平台/店铺
 * @author jiaoyuqiang
 *
 */
@Service("pltmService")
public class PltmServiceImp extends DefaultServiceImp<IdEntity, Long> implements
		PltmService {

	private PltmDao pltmDao;

	@Autowired
	public void setPltmDao(PltmDao pltmDao) {
		this.pltmDao = pltmDao;
		this.dao=pltmDao;
	}
}
