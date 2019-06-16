package org.zyw.secondkill.controller.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ItemVO {
	private Integer id;
	// 名称
	private String title;
	// 价格
	private BigDecimal price;
	// 库存
	private Integer stock;
	// 描述
	private String description;
	// 销量
	private Integer sales;
	// 图片URL
	private String imgUrl;

	// 冗余秒杀信息:0:没有秒杀活动 1：秒杀待开始 2：秒杀已经开始
	private Integer status;

	// 秒杀活动价格
	private BigDecimal secKillPrice;

	// 秒杀活动ID
	private Integer secKillId;

	// 秒杀活动开始时间
	private LocalDateTime startTime;

}
