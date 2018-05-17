package com.e3mall.content.service;

import java.util.List;

import com.e3mall.pojo.TbContent;

public interface ContentService {
	public List<TbContent> getContentListByCid(long categoryId);
}
