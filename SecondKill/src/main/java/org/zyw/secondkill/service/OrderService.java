package org.zyw.secondkill.service;

import org.zyw.secondkill.service.model.OrderModel;

public interface OrderService {
	// 秒杀活动有两种方式:
	// 1、前端URL传过来秒杀活动ID，然后内部校验秒杀活动ID对应的活动是否开始
	// 2、直接在下单接口内判断是否有秒杀活动，如果有，以秒杀价格下单
	// 我们选择使用方案一：因为一个商品可能有多个秒杀活动，如果每个订单判断是否有活动，平销商品全部都要判断
	OrderModel createOrder(Integer userId, Integer itemId, Integer secKillId, Integer quantity);
}
