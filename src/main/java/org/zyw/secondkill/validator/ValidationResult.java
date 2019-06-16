package org.zyw.secondkill.validator;

import java.util.HashMap;

import lombok.Data;

@Data
public class ValidationResult {
	private boolean hasErrors = false;
	private HashMap<String, String> errMsgMap = new HashMap<String, String>();

	public String getErrMsg() {
		return String.join(",", errMsgMap.values());
	}

}
