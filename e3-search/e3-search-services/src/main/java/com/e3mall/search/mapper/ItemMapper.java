package com.e3mall.search.mapper;

import java.util.List;

import com.e3mall.common.pojo.SearchItem;
/**
 * 
 * @author zjt
 * @Description: 导入商品用的
 * @date 2018年3月19日 下午8:04:24
 *
 */
public interface ItemMapper {
	
	public List<SearchItem> getItemList();
	public SearchItem getItemById(long itemId);
}
