package com.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.untils.E3Result;
import com.e3mall.content.service.ContentCatListService;
import com.e3mall.pojo.TbContent;
/**
 * 
 * @author zjt
 * @Description: 内容管理
 * @date 2018年3月16日 下午4:19:26
 *
 */
@Controller
public class ContentCatController {
	
	@Autowired
	private ContentCatListService contentCatListService;
	
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getEasyUITreeNodes(long categoryId,Integer page,Integer rows){
		
		System.out.println(categoryId+" "+page+" "+rows+"=====================");
		
		EasyUIDataGridResult easyUITreeNodes = contentCatListService.getEasyUITreeNodes(categoryId, page, rows);
		
//		List<TbContent> contents = (List<TbContent>) easyUITreeNodes.getRows();
//		for (TbContent tbContent : contents) {
//			System.out.println(tbContent.toString());
//		}
		return easyUITreeNodes;
	} 
	
	/**删除内容*/
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteContent(long[] ids){
		E3Result e3Result = contentCatListService.deleteContent(ids);
		return e3Result;
	}
	
	/**新增内容*/
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result addContent(TbContent tbContent){
		E3Result e3Result = contentCatListService.addContent(tbContent);
		return e3Result;
	}
	
	/**更新内容*/
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result updateContent(TbContent tbContent) {
		E3Result e3Result = contentCatListService.updateContent(tbContent);
		return e3Result;
	}
}
