package org.zyw.secondkill.error;

public enum EmBusinessError implements CommonError {

	INVALID_PAREMETER(40000, "参数不正确"),

	USER_NOT_EXIST(10001, "用户不存在"),

	REGISTER_FAIL(10002, "用户注册失败"),

	USER_LOGIN_FAIL(10003, "用户手机号或密码不正确"),

	USER_UNLOGIN(10004, "用户未登录"),

	STOCK_NOT_ENOUGH(30001, "库存不足"),

	UNKNOW_ERROR(50000, "未知错误"),

	;

	private Integer errCode;
	private String errMsg;

	private EmBusinessError(Integer errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	@Override
	public Integer getErrCode() {
		return this.errCode;
	}

	@Override
	public String getErrMsg() {
		return this.errMsg;
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}

}
