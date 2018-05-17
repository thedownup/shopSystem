package com.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author zjt
 * @Description: 搜索结构POJO
 * @date 2018年3月19日 下午10:37:58
 *
 */
public class SearchResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<SearchItem> itemList;
	private int totalPages;
	private int recourdCount;
	
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public int getRecourdCount() {
		return recourdCount;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public void setRecourdCount(int recourdCount) {
		this.recourdCount = recourdCount;
	}
}
