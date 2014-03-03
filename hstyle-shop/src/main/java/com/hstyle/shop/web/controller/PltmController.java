/**
 * spring-mvc前端控制类
 * @author Administrator
 *
 */
package com.hstyle.shop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.shop.service.PltmService;


@Controller
@RequestMapping("/pltm")
public class PltmController extends BaseController{
	@Autowired
	private PltmService pltmService;

}
