package com.e3mall.search.serviceImpl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e3mall.common.pojo.SearchItem;
import com.e3mall.common.pojo.SearchResult;
import com.e3mall.common.untils.E3Result;
import com.e3mall.search.dao.SearchDao;
import com.e3mall.search.mapper.ItemMapper;
import com.e3mall.search.service.SearchService;
/**
 * 
 * @author zjt
 * @Description: 搜索商品 
 * @date 2018年3月19日 下午10:44:33
 *
 */
@Service
public class SearchServiceImpl implements SearchService{

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SearchDao searchDao;
	@Value("${DEFAULT_FIELD}")
	private String DEFAULT_FIELD;


	@Override
	public SearchResult search(String keyWord, int page, int rows) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		//设置查询条件
		solrQuery.setQuery(keyWord);
		//设置分页条件
		solrQuery.setStart((page - 1) * rows);
		//设置rows
		solrQuery.setRows(rows);
		//设置默认搜索域
		solrQuery.set("df", DEFAULT_FIELD);
		//设置高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		SearchResult searchResult = searchDao.search(solrQuery);
		//计算总页数
		int recourdCount = searchResult.getRecourdCount();
		int pages = recourdCount / rows;
		if (recourdCount % rows > 0) pages++;
		//设置到返回结果
		searchResult.setTotalPages(pages);
		return searchResult;

	}
	
	/** 同步到solr中*/
	public E3Result addDocument(long itemId) throws Exception {
		// 1、根据商品id查询商品信息。
		SearchItem searchItem = itemMapper.getItemById(itemId);
		// 2、创建一SolrInputDocument对象。
		SolrInputDocument document = new SolrInputDocument();
		// 3、使用SolrServer对象写入索引库。
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getCategory_name());
		// 5、向索引库中添加文档。
		solrServer.add(document);
		solrServer.commit();
		// 4、返回成功，返回e3Result。
		return E3Result.ok();
	}

}
