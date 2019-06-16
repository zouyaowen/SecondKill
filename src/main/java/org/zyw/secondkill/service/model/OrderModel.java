package org.zyw.secondkill.service.model;

import java.math.BigDecimal;

import lombok.Data;

//用户下单的交易模型
@Data
public class OrderModel {
	// 订单唯一标识
	private String id;
	// 用户唯一标识
	private Integer userId;

	// 秒杀活动唯一标识
	private Integer secKillId;

	// 商品唯一标识
	private Integer itemId;
	// 购买商品当时的价格，若secKillId非空，则为秒杀商品价格
	private BigDecimal itemPrice;
	// 购买数量
	private Integer itemQuantity;
	// 购买金额，若secKillId非空，则为秒杀商品金额
	private BigDecimal orderAmount;
}
