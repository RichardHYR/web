package top.ivyxo.web.common.data;

/**
 * 业务常用状态码 Richard - 2019-12-2 15:05:01
 */
public enum EServiceCode {
	SUCCESS("成功",1),
	INVALID_PARAM("非法入参",500),
	ERROR("业务异常",501);

	private String msg;

	public Integer code;

	private EServiceCode(String msg, Integer code) {
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
