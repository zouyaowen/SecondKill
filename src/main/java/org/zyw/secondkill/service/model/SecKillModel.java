package org.zyw.secondkill.service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SecKillModel {
	private Integer id;

	// 秒杀活动状态 1:未开始 2：已经开始 3：已结束
	private Integer status;

	// 活动名称
	private String secKillName;

	// 秒杀开始时间
	private LocalDateTime startTime;

	// 秒杀结束时间
	private LocalDateTime endTime;

	// 秒杀商品唯一标识
	private Integer itemId;

	// 秒杀活动商品价格
	private BigDecimal secKillPrice;

}
