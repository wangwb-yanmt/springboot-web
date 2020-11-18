package com.wangwb.web.common.bean;


/**
 * 	返回json数据	
 * @author wangwb
 *
 */
public class JsonResult {

	private int code;
	
	private boolean success;
	
	private String msg;
	
	private Object data;
	
	public JsonResult() {}
	
	public JsonResult(boolean success, int Code, String msg, Object data) {
    	this.success = success;
    	this.code = Code;
    	this.msg = msg;
    	this.data = data;
    }
    
	public JsonResult(boolean success, int Code, String msg) {
		this.success = success;
    	this.code = Code;
    	this.msg = msg;
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
