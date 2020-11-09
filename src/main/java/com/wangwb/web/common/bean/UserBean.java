package com.wangwb.web.common.bean;

import java.io.Serializable;

/**
 * 	用户信息实体
 * @author wangwb
 *
 */
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//登陆用户Id
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
