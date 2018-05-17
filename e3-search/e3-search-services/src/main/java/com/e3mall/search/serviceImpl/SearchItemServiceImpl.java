package com.e3mall.search.serviceImpl;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.common.pojo.SearchItem;
import com.e3mall.common.untils.E3Result;
import com.e3mall.search.mapper.ItemMapper;
import com.e3mall.search.service.*;

@Service
public class SearchItemServiceImpl implements SearchItemService{

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private SolrServer solrServer;

	@Override
	public E3Result importItem() {
		try {
			List<SearchItem> searchItems = itemMapper.getItemList();

			for (SearchItem searchItem : searchItems) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				//写入索引库
				solrServer.add(document);
			}
			//提交
			solrServer.commit();
			return E3Result.ok();
		} catch (Exception e) {
			return E3Result.build(500, "商品导入失败");
		}

	}

}
