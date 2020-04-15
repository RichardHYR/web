package top.ivyxo.web.common.data;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 输出的格式实体 - 2019-12-1 21:31:17
 * @author HYR
 */
@ApiModel(value = "通用实体", description = "通用实体类，真正的数据res")
public class ResponseObj<T> implements Serializable {

	private static final long serialVersionUID = -4977640210257037759L;

	public ResponseObj() {

	}

	public ResponseObj(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	@ApiModelProperty(value = "响应码")
	public Integer code = EStatusCode.SUCCESS.getCode();

	@ApiModelProperty(value = "响应信息")
	public String msg;

	@ApiModelProperty(value = "真正的数据")
	public T data;

}
