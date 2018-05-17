package com.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.untils.E3Result;
import com.e3mall.pojo.TbItem;
import com.e3mall.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	private TbItem getItemById(@PathVariable long itemId) {
		TbItem tbItem = itemService.getTbItemById(itemId);
		return tbItem;
	}
	
	//商品添加
	@RequestMapping("/item/save")
	@ResponseBody
	public E3Result addItem(TbItem item, String desc){
		E3Result addItem = itemService.addItem(item, desc);
		return addItem;
	}
	
	//商品编辑
	@RequestMapping("rest/page/item-edit")
	@ResponseBody
	public static void name() {
		
	}
	
	//商品删除
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public E3Result deleteItem(long... ids){
		E3Result deleteItem = itemService.deleteItem(ids);
		return deleteItem;
	}
	
	//下架
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public E3Result instock(long... ids){
		E3Result instock = itemService.instock(ids);
		return instock;
	}
	
	//上架
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public E3Result reshelf(long... ids){
		E3Result reshelf = itemService.reshelf(ids);
		return reshelf;
	}
	
}
