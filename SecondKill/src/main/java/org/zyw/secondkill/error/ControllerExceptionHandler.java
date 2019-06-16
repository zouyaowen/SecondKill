package org.zyw.secondkill.error;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zyw.secondkill.response.CommonReturnType;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	/**
	 * @Desc 业务异常处理
	 * @param ex
	 * @return CommonReturnType
	 * @Author zyw
	 * @Date 2019年6月2日下午9:09:51
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonReturnType handleRuntimeException(RuntimeException ex) {
		if (ex instanceof BusinessException) {
			BusinessException businessException = (BusinessException) ex;
			log.error("errMsg={}", businessException.getErrMsg());
			HashMap<String, Object> responseData = new HashMap<>();
			responseData.put("errCode", businessException.getErrCode());
			responseData.put("errMsg", businessException.getErrMsg());
			return CommonReturnType.create("failure", responseData);
		} else {
			log.error("errMsg={}", ex.getMessage());
			log.error("errMsg={}", ex.getClass().getName());
			HashMap<String, Object> responseData = new HashMap<>();
			responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrCode());
			responseData.put("errMsg", EmBusinessError.UNKNOW_ERROR.getErrMsg());
			responseData.put("errType", ex.getClass().getName());
			return CommonReturnType.create("failure", responseData);
		}
	}
}
