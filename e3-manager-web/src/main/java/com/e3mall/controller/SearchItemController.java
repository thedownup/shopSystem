package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.untils.E3Result;
import com.e3mall.search.service.SearchItemService;

/**
 * 
 * @author zjt
 * @Description: 一键导入solr
 * @date 2018年3月19日 下午9:01:34
 *
 */
@Controller
public class SearchItemController {
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItem(){
		E3Result e3Result = searchItemService.importItem();
		return e3Result;
	}
}

