package top.ivyxo.web.common.data;

/**
 * 常用状态码
 */
public enum EStatusCode {
	SUCCESS("成功","200"),
	INVALID_PARAM("非法入参","20001"),
	RMDB_ERR("数据库异常","20002"),
	NOTLOGIN("请先登录","200011"),
	UNKNOWN_ERR("未知错误","500000");

	private String msg;
	
	public String code;

	private EStatusCode(String msg, String code) {
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}
	
}
