package org.zyw.secondkill.entity;

import lombok.Data;

@Data
public class SequenceInfoDO {
	private Integer id;
	private String name;
	private Integer currentValue;
	private Integer step;
}
