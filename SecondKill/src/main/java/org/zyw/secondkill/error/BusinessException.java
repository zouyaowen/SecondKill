package org.zyw.secondkill.error;

public class BusinessException extends RuntimeException implements CommonError {

	private static final long serialVersionUID = 1L;
	
	private EmBusinessError emBusinessError;

	public BusinessException(EmBusinessError emBusinessError) {
		super();
		this.emBusinessError = emBusinessError;
	}

	public BusinessException(EmBusinessError emBusinessError, String errMsg) {
		super();
		this.emBusinessError = emBusinessError;
		this.emBusinessError.setErrMsg(errMsg);
	}

	@Override
	public Integer getErrCode() {
		return this.emBusinessError.getErrCode();
	}

	@Override
	public String getErrMsg() {
		return this.emBusinessError.getErrMsg();
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		this.emBusinessError.setErrMsg(errMsg);
		return this;
	}

}
