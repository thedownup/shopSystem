package com.e3mall.search.service;


import com.e3mall.common.pojo.SearchResult;
import com.e3mall.common.untils.E3Result;

public interface SearchService {

	public SearchResult search(String keyWord, int page, int rows) throws Exception;
	public E3Result addDocument(long itemId) throws Exception;
}
