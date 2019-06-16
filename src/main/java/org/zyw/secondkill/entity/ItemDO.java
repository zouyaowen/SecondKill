package org.zyw.secondkill.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemDO {
	private Integer id;
	// 名称
	private String title;
	// 价格
	private BigDecimal price;
	// 描述
	private String description;
	// 销量
	private Integer sales;
	// 图片URL
	private String imgUrl;

	// 秒杀商品价格
	private Integer secKillId;
}
