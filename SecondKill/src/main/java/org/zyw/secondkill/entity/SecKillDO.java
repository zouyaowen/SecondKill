package org.zyw.secondkill.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SecKillDO {
	private Integer id;
	private String secKillName;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer itemId;
	private BigDecimal secKillPrice;
}
