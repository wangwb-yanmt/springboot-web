package com.wangwb.web.common.myenum;

/**
 * 返回码
 * @author wangwb@sparknet.com.cn
 *
 */
public enum ResultCode {

	//通用成功
	SUCCESS(0, "成功"),

	//异常
	RUNTIME_ERROR(-2,"哦哦，发生运行时异常了"),
	ERROR(-1, "哦哦，发生异常了"),
    
	//参数相关
	PARAM_NOT_VALID(1001, "请检查参数名称是否正确"),
	PARAM_VALUE_EMPTY(1002, "请检查参数的值是否为空"),
	PARAM_TYPE_ERROR(1003, "请检查参数值的类型是否有误"),
	PAGE_PARAM_ERROR(1004,"请检查分页参数是否正确"),
	
	//登录账号相关
	USER_NOT_LOGIN(2001, "当前未登录，请先登录"),
	USER_ACCOUNT_EXPIRED(2002, "无效的登录信息，请重新登录"),
	USER_ACCOUNT_DISABLE(2003, "账号不可用，请联系管理员"),
	USER_ACCOUNT_LOCKED(2004, "账号被锁定，请联系管理员"),
	USER_ACCOUNT_NOT_EXIST(2005, "账号不存在，请重新登录"),
	USER_ACCOUNT_USE_BY_OTHERS(2006, "账号下线通知，请重新登录"),
	
	USER_ACCOUNT_ERROR(2007, "用户名或密码错误"),
	
	NO_PERMISSION(3001, "没有权限");
	
	private Integer code;
	
	private String message;
	
	ResultCode(Integer code, String message) {
	    this.code = code;
	    this.message = message;
	}
	
	public Integer getCode() {
	    return code;
	}
	
	public void setCode(Integer code) {
	    this.code = code;
	}
	
	public String getMessage() {
	    return message;
	}
	
	public void setMessage(String message) {
	    this.message = message;
	}
	
}
