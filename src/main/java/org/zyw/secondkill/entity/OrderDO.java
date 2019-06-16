package org.zyw.secondkill.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderDO {
	// 订单唯一标识
	private String id;
	// 用户唯一标识
	private Integer userId;
	// 商品唯一标识
	private Integer itemId;
	// 购买商品当时的价格
	private BigDecimal itemPrice;
	// 购买数量
	private Integer itemQuantity;
	// 购买金额
	private BigDecimal orderAmount;
}
