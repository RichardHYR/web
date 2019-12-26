package top.ivyxo.web.common.data;

/**
 * 常用状态码
 * @author HYR
 */
public enum EStatusCode {
	//20000公共
	SUCCESS("成功",200),

	//50000未知错误
	UNKNOWN_ERR("未知错误",50000),
	RMDB_ERR("数据库异常",50001),
	INVALID_PARAM("非法入参",50002),

	//40000业务错误
	CHECK_NOT_LOGIN("用户未登录",40101),
	NOT_LOGIN("请先登录",400102),

	ACCOUNT_EXIST("该账户已存在",400201),
	PASSWORD_FAIL("密码输入不一致",400203),

	ACCOUNT_PASSWORD_MISTAKE("账户或密码错误",400301);

	private String msg;
	
	public Integer code;

	private EStatusCode(String msg, Integer code) {
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
