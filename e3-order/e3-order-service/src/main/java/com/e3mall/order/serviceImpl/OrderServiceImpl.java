package com.e3mall.order.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.e3mall.common.jedis.JedisClient;
import com.e3mall.common.untils.E3Result;
import com.e3mall.mapper.TbOrderItemMapper;
import com.e3mall.mapper.TbOrderMapper;
import com.e3mall.mapper.TbOrderShippingMapper;
import com.e3mall.order.service.OrderService;
import com.e3mall.pojo.TbOrderItem;
import com.e3mall.pojo.TbOrderShipping;
import com.w3mall.order.pojo.OrderInfo;

/**
 * @author zjt
 * @Description: 处理订单
 */
@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_ID_BEGIN}")
	private String ORDER_ID_BEGIN;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;
	
	
	@Override
	public E3Result createOrder(OrderInfo orderInfo) {
		
		//生成订单号
		if (!jedisClient.exists(ORDER_GEN_KEY)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_ID_BEGIN);
		}
		
		String orderId = jedisClient.incr(ORDER_GEN_KEY).toString();
		orderInfo.setOrderId(orderId);
		orderInfo.setPostFee("0");
		//1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		// 向订单表插入数据。
		orderMapper.insert(orderInfo);
		// 向订单明细表插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//生成明细id
			Long orderItemId = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY);
			tbOrderItem.setId(orderItemId.toString());
			tbOrderItem.setOrderId(orderId);
			//插入数据
			orderItemMapper.insert(tbOrderItem);
		}
		//向订单物流表插入数据。
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		
		return E3Result.ok(orderId);
	}

}
