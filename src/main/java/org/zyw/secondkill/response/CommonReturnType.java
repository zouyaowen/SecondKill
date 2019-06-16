package org.zyw.secondkill.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonReturnType {
	// success 、failure
	private String status;
	private Object data;

	public static CommonReturnType create(Object result) {
		return CommonReturnType.create("success", result);
	}

	public static CommonReturnType create(String status, Object result) {
		return new CommonReturnType(status, result);
	}
}
