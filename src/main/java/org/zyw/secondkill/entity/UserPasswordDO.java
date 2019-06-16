package org.zyw.secondkill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDO {
	private Integer id;
	private String encriptPassword;
	private Integer userId;
}
