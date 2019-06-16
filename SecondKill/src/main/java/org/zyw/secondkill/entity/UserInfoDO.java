package org.zyw.secondkill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDO {
	private Integer id;
	private String name;
	private Byte gender;
	private Integer age;
	private String telphone;
	private String registerMode;
	private String thirdPartyId;
}
