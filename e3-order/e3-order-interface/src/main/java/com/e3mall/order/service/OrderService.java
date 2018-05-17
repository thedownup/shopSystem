package com.e3mall.order.service;

import com.e3mall.common.untils.E3Result;
import com.w3mall.order.pojo.OrderInfo;

public interface OrderService {

	E3Result createOrder(OrderInfo orderInfo);
}
