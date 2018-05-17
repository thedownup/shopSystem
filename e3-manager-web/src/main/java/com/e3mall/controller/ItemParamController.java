package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.service.ItemParamService;

@Controller
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/item/param/list")
	@ResponseBody
	public EasyUIDataGridResult getEasyUIDataGridResult(Integer page, Integer rows){
		EasyUIDataGridResult easyUIDataGridResult = itemParamService.getEasyUIDataGridResult(page, rows);
		return easyUIDataGridResult;
	}
}
