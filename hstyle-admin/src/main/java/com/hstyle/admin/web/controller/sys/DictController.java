/**
 * 数据字典管理spring-mvc前端控制类
 * @author Administrator
 *
 */
package com.hstyle.admin.web.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hstyle.admin.model.Dictionary;
import com.hstyle.admin.model.DictionaryMapping;
import com.hstyle.admin.model.DictionaryType;
import com.hstyle.admin.service.DictionaryService;
import com.hstyle.admin.web.controller.BaseController;
import com.hstyle.admin.web.controller.Msg;
import com.hstyle.framework.dao.core.Page;
import com.hstyle.framework.dao.core.PageRequest;


@Controller
@RequestMapping("/dict")
public class DictController extends BaseController{
	@Autowired
	private DictionaryService dictionaryService;
	private static final String COMMON_YES = "1";//正常状态
	/**
	 * 分页查询数据字典
	 * @param code
	 * @param name
	 * @param request
	 * @return
	 */
    @RequestMapping("listDictType")
    public @ResponseBody Page<DictionaryType> listDictType(HttpServletRequest request) {
    	logger.info("list dict ...");
    	PageRequest pageRequest=buildPageRequest(request);//分页参数
    	DictionaryType paramDTO = new DictionaryType();
    	paramDTO.setIsdel(COMMON_YES);
    	Page<DictionaryType> dictionaryType = dictionaryService.listDictionaryType(pageRequest , paramDTO);
		return dictionaryType;
	}
    
    /**
     * 保存或更新字典{主信息}
     * @param dictionaryType
     * @return
     */
    @RequestMapping("updateDictType")
    public @ResponseBody Msg updateDictType(@Valid DictionaryType dictionaryType) {
    	logger.info("METHOD:{updateDict} REMARK:{save or update  dict ...}");
    	dictionaryType.setIsdel(COMMON_YES);
    	dictionaryService.save(dictionaryType);
    	return success(dictionaryType);
    }
    
    /**
     * 保存或更新字典{子信息}
     * @param dictionary
     * @return
     */
    @RequestMapping("updateDict")
    public @ResponseBody Msg updateDict(@Valid Dictionary dictionary) {
    	logger.info("METHOD:{updateDictItem} REMARK:{save or update dictitem ...}");
    	DictionaryType main = new DictionaryType();
    	main.setId(dictionary.getParentid());
    	dictionary.setDictionaryType(main);
    	dictionary.setIsdel(COMMON_YES);
    	dictionaryService.saveOrUpdateDictionary(dictionary);
    	return success(true);
    }
    
    /**
     * 根据外键(父id)查询子信息
     * @param parentid
     * @return
     */
    @RequestMapping("listDict")
    public @ResponseBody Page<Dictionary> listDict(@RequestParam Long parentid) {
    	logger.info("METHOD:{listDict} REMARK:{query  dictitem ...}");
    	List<Dictionary> list = dictionaryService.getDictionariesByTypeid(parentid);;
    	Page<Dictionary> page = new Page<Dictionary>();
    	page.setResult(list);
    	return page;
    }
    
    /**
     * 根据id批量逻辑删除数据字典{主表}信息
     * @param ids (以','分割的id串)
     * @return
     */
    @RequestMapping("delDictType")
    public @ResponseBody Msg delDictType(@RequestParam String ids) {
    	dictionaryService.delDictType(ids);
    	return success("操作成功");
    }
    
    /**
     * 根据id批量逻辑删除数据字典{子表}信息
     * @param ids
     * @return
     */
    @RequestMapping("delDict")
    public @ResponseBody Msg delDict(@RequestParam String ids) {
    	dictionaryService.delDict(ids);
    	return success("操作成功");
    }
    
    /**
     * 根据id批量逻辑删除数据字典{映射表}信息
     * @param ids
     * @return
     */
    @RequestMapping("delMapping")
    public @ResponseBody Msg delMapping(@RequestParam String ids) {
    	dictionaryService.delMapping(ids);
    	return success("操作成功");
    }   
    
    /**
     * 根据外键(父id)查询子信息
     * @param parentid
     * @return
     */
    @RequestMapping("listMapping")
    public @ResponseBody Page<DictionaryMapping> listMapping(@RequestParam Long parentid) {
    	logger.info("listMapping .......");
    	List<DictionaryMapping> list = dictionaryService.getMappingByDictId(parentid);
    	Page<DictionaryMapping> page = new Page<DictionaryMapping>();
    	page.setResult(list);
    	return page;
    }    
    
    /**
     * 保存或更新字典{子信息}
     * @param dictionary
     * @return
     */
    @RequestMapping("updateMapping")
    public @ResponseBody Msg updateMapping(@Valid DictionaryMapping dictionaryMapping) {
    	logger.info("updateMapping......");
    	Dictionary main = new Dictionary();
    	main.setId(dictionaryMapping.getDictId());
    	dictionaryMapping.setDictionary(main);
    	dictionaryMapping.setStatus(COMMON_YES);
    	dictionaryService.saveOrUpdateMapping(dictionaryMapping);
    	return success(true);
    }    
    
    /**
     * 根据数据字典主表code查询
     * @param code
     * @return
     */
    @RequestMapping("findDictionaryByCode")
    public @ResponseBody Msg findDictionaryByCode(@RequestParam String code) {
    	logger.info("excute findDictionaryByCode Method...");
    	return success(dictionaryService.findDictionaryByParentCode(code));
    }
    
}
