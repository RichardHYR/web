package com.ivyxo.web.common.data;


import java.io.Serializable;

/**
 * 输出的格式实体 - 2019-12-1 21:31:17
 * @author HYR
 */
public class ResponseObj<T> implements Serializable {

	private static final long serialVersionUID = -4977640210257037759L;

	public ResponseObj() {

	}

	public ResponseObj(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String code = EStatusCode.SUCCESS.getCode();
	
	public T data;
	
	public String msg;
	
	
	
}
