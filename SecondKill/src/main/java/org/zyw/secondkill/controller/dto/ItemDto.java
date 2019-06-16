package org.zyw.secondkill.controller.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ItemDto {
	@NotBlank(message = "商品名称不能为空")
	private String title;
	@NotBlank(message = "商品描述不能为空")
	private String description;
	@NotNull(message = "商品价格不能为空")
	@Min(value = 0, message = "价格不能小于0")
	private BigDecimal price;
	@NotNull(message = "商品库存不能为空")
	@Min(value = 0, message = "库存不能小于0")
	private Integer stock;
	@NotBlank(message = "商品图片信息不能为空")
	private String imgUrl;
}
