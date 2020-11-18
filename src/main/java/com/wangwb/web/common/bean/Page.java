package com.wangwb.web.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *	查询列表分页对象返回
 * @author wangwb
 *
 */
public class Page {
	
	//是否成功
	private boolean success;
	
	//返回码
	private int code;
	
	//提示信息
	private String msg ;
	
	//列表总数
	private int count;
	
	//列表数据list
	private List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
	
	public Page() {}
	
	public Page(boolean success,int code,String msg,int count,List<Map<String,Object>> list) {
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = list;
	}
	
	public Page(boolean success,int code,String msg) {
		this.success = success;
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
        return code;
    }

	public void setCode(int code) {
        this.code = code;
    }

	public int getCount() {
		return count;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Map<String, Object>> getList() {
		return data;
	}

	public void setList(List<Map<String, Object>> list) {
		this.data = list;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
