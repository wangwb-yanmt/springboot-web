package com.wangwb.web.common.bean;

import java.util.function.Predicate;

/**
 * 	返回json数据	
 * @author wangwb
 *
 */
public class BaseResponse<T> {

	private int code;
	private boolean success;
    private String msg;
    private T data;

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
