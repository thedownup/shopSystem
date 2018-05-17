package com.e3mall.content.service;

import java.util.List;

import com.e3mall.common.pojo.EasyUITreeNode;
import com.e3mall.common.untils.E3Result;

public interface ContentCategoryService {
	
	public List<EasyUITreeNode> getContentCategoryList(long patentId);
	public E3Result addContentCategoryList(long patentId,String name);
	public E3Result deleteContentCategoryList(long id);
	public E3Result updateContentCategory(long id,String name);
}
