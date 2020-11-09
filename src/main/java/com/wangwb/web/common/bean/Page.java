package com.wangwb.web.common.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *	查询列表分页对象
 * @author wangwb
 *
 */
public class Page {

	//列表总数
	private int count = 0;
	
	//提示信息
	private String msg = "";
	
	//数据list
	private List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
