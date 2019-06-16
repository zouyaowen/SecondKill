package org.zyw.secondkill.service.model;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ItemModel {
	private Integer id;
	// 名称
	@NotBlank(message = "商品名称不能为空")
	private String title;
	// 价格
	@NotNull(message = "商品价格不能为空")
	@Min(value = 0, message = "商品价格不能小于0")
	private BigDecimal price;
	// 库存
	@NotNull(message = "库存不能不填")
	private Integer stock;
	// 描述
	@NotBlank(message = "商品描述信息不能为空")
	private String description;
	// 销量
	private Integer sales;
	// 图片URL
	@NotBlank(message = "商品图片信息不能为空")
	private String imgUrl;

	// 如果secKillModel不为空，标识该商品有还未结束的秒杀活动，包含未开始的秒杀活动
	private SecKillModel secKillModel;
}
