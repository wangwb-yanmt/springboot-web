package com.wangwb.web.common.exception;

/**
 * 自定义运行时异常
 * @author wangwb@sparknet.com.cn
 *
 */
public class MyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;
	
	private String msg;
	
	public MyException(int code, String msg) {
		this.setCode(code);
		this.setMsg(msg);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
