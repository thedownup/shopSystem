package com.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.pojo.EasyUITreeNode;
import com.e3mall.service.ItemCatService;

@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	
	/**
	 * 返回商品类目选择
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> geTreeNodes(@RequestParam(value="id",defaultValue="0")long parentId){
		List<EasyUITreeNode> easyUITreeNodes = itemCatService.getEasyUITreeNodes(parentId);
		return easyUITreeNodes;
	}
	
}
