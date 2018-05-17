package com.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.e3mall.common.pojo.EasyUITreeNode;
import com.e3mall.common.untils.E3Result;
import com.e3mall.content.service.ContentCategoryService;

/**
 * 
 * @author zjt
 * @Description: 内容服务
 * @date 2018年3月15日 下午6:40:46
 *
 */
@Controller
public class ContentController {

	@Autowired
	private ContentCategoryService categoryService;

	
	/**查询*/
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(
			@RequestParam(name="id",defaultValue="0")long patentId){
		List<EasyUITreeNode> contentCategoryList = categoryService.getContentCategoryList(patentId);
		return contentCategoryList;
	}
	
	/**内容添加*/
	@RequestMapping(value="/content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public E3Result addContentCategoryList(Long parentId,String name){
		E3Result e3Result = categoryService.addContentCategoryList(parentId, name);
		return e3Result;
	}
	
	/**重命名类目*/
	@RequestMapping(value="/content/category/update",method=RequestMethod.POST)
	@ResponseBody
	public E3Result updateContentCategory(long id,String name){
		E3Result e3Result = categoryService.updateContentCategory(id, name);
		return e3Result;
	}
	
	
	/**内容删除*/
	@RequestMapping(value="/content/category/delete",method=RequestMethod.POST)
	@ResponseBody
	public E3Result deleteContentCategoryList(long id){
		E3Result e3Result = categoryService.deleteContentCategoryList(id);
		return e3Result;
	}
}
