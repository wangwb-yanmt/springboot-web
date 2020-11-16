package com.wangwb.web.common.bean;


/**
 * 	返回json数据	
 * @author wangwb
 *
 */
public class BaseResponse {

	private int code;
	private boolean success;
	private String msg;
	private Object data;
    
	public BaseResponse(int code, boolean success, String msg, Object data) {
    	this.code = code;
    	this.success = success;
    	this.msg = msg;
    	this.data = data;
    }
	
	public BaseResponse(boolean success, String msg, Object data) {
    	this.success = success;
    	this.msg = msg;
    	this.data = data;
    }

	public int getCode() {
        return code;
    }

	public void setCode(int code) {
        this.code = code;
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMsg() {
        return msg;
    }

	public void setMsg(String msg) {
        this.msg = msg;
    }

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
