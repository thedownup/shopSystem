package com.e3mall.service;

import java.util.List;

import com.e3mall.common.pojo.EasyUITreeNode;


public interface ItemCatService {
	public List<EasyUITreeNode> getEasyUITreeNodes(long parentID);
}
