package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.service.ItemService;

/**
 * 
 * @author zjt
 * @Description: 页面引导控制器
 * @date 2018年2月25日 下午1:46:20
 *
 */
@Controller
public class pageController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/")
	//首页	
	public String index(){
		return "index";
	}
	
	@RequestMapping("/{showName}")
	public String showIndex(@PathVariable String showName){
		return showName;
	}
	
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult showList(Integer page,Integer rows){
		EasyUIDataGridResult easyUIDataGridResult = itemService.getEasyUIDataGridResult(page, rows);
		return easyUIDataGridResult;
	}
}
