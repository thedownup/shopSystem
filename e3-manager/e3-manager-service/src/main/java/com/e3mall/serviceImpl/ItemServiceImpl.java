package com.e3mall.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.pojo.EasyUIDataGridResult;
import com.e3mall.common.untils.E3Result;
import com.e3mall.common.untils.IDUtils;
import com.e3mall.common.untils.JsonUtils;
import com.e3mall.mapper.TbItemDescMapper;
import com.e3mall.mapper.TbItemMapper;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;
import com.e3mall.pojo.TbItemExample;
import com.e3mall.pojo.TbItemExample.Criteria;
import com.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import sun.tools.jar.resources.jar;

@Service("itemServiceImpl")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEM_INFO_EXPIRE}")
	private String ITEM_INFO_EXPIRE;

	@Override
	public TbItem getTbItemById(long id) {
		//添加缓存
		try {
			//查询缓存
			String json = jedisClient.get("ITEM_INFO" + ":" + id + ":BASE");
			if (StringUtils.isNotBlank(json)) {
				//把json转换为java对象
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
		try {
			//把数据保存到缓存
			jedisClient.set("ITEM_INFO" + ":" + id + ":BASE", JsonUtils.objectToJson(tbItem));
			//设置缓存的有效期
			jedisClient.expire("ITEM_INFO" + ":" + id + ":BASE", Integer.valueOf(ITEM_INFO_EXPIRE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;
	}

	/**获得商品描述*/
	public TbItemDesc getItemDescById(long itemId) {
		try {
			String json = jedisClient.get("ITEM_INFO_PRE" + ":" + itemId + ":DESC");
			//判断缓存是否命中
			if (StringUtils.isNotBlank(json) ) {
				//转换为java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		try {
			jedisClient.set("ITEM_INFO_PRE" + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
			//设置过期时间
			jedisClient.expire("ITEM_INFO_PRE" + ":" + itemId + ":DESC", Integer.valueOf(ITEM_INFO_EXPIRE));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return itemDesc;
	}


	public EasyUIDataGridResult getEasyUIDataGridResult(Integer page,Integer rows) {

		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//创建一个返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

	//添加商品
	public E3Result addItem(TbItem item ,String desc) {
		// 1、生成商品id
		final long itemId = IDUtils.getItemId();
		item.setId(itemId);
		//商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		tbItemMapper.insert(item);
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		tbItemDescMapper.insert(itemDesc);
		//发送一个商品添加消息 更新solr
		jmsTemplate.send(topicDestination,new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}
		});
		return E3Result.ok();
	}

	//删除商品
	public E3Result deleteItem(long... ids) {
		for (int i = 0; i < ids.length; i++) {
			tbItemMapper.deleteByPrimaryKey(ids[i]);
		}
		return E3Result.ok();
	}

	//上架
	public E3Result reshelf(long... ids) {
		for (int i = 0; i < ids.length; i++) {
			TbItem tbItem = new TbItem();
			tbItem.setId(ids[i]);
			tbItem.setStatus((byte)1);
			tbItemMapper.updateByPrimaryKeySelective(tbItem);
		}

		return E3Result.ok();
	}

	//下架
	public E3Result instock(long... ids) {
		for (int i = 0; i < ids.length; i++) {
			TbItem tbItem = new TbItem();
			tbItem.setId(ids[i]);
			tbItem.setStatus((byte)2);
			tbItemMapper.updateByPrimaryKeySelective(tbItem);
		}

		return E3Result.ok();
	}
}
