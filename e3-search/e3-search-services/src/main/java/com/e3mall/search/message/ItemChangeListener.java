package com.e3mall.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.e3mall.search.service.SearchService;
import com.e3mall.search.serviceImpl.SearchServiceImpl;
/**
 * 更新solr 根据e3-manager-service增加商品
 * @author zjt
 * @Description: TODO
 * 
 *
 */
public class ItemChangeListener implements MessageListener{
	
	@Autowired
	private SearchService searchServiceImpl;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = null;
			Long itemId = null; 
			//取商品id
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				itemId = Long.parseLong(textMessage.getText());
			}
			//向索引库添加文档
			searchServiceImpl.addDocument(itemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
