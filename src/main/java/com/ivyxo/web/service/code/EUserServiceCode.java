package com.ivyxo.web.service.code;

/**
 * 业务常用状态码 Richard - 2019-12-2 15:05:01
 */
public enum EUserServiceCode {
	SUCCESS("成功",1),
	FAIL("失败",500),
	ACCOUNT_EXIST("该账户已存在",501),
	ACCOUNT_NOT_EXIST("该账户不存在",502),
	PASSWORD_FAIL("密码输入不一致",503),
	PASSWORD_MISTAKE("密码错误",504);

	private String msg;

	public Integer code;

	private EUserServiceCode(String msg, Integer code) {
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public Integer getCode() {
		return code;
	}

}
