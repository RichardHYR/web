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
	ACCOUNT_PASSWORD_FAIL("密码输入不一致",400203),

	UPDATE_PASSWORD_REPEAT("新密码不能与旧密码相同",400301),
	UPDATE_PASSWORD_NOT_SAME("二次确认密码不相同",400302),
	UPDATE_PASSWORD_FAIL("旧密码错误",400303),

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
