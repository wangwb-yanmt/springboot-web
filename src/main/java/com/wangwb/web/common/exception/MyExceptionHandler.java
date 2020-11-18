package com.wangwb.web.common.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangwb.web.common.bean.JsonResult;
import com.wangwb.web.common.myenum.ResultCode;


/**
 * 	全局异常处理
 * @author wangwb
 *
 */
@ControllerAdvice
public class MyExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);
	
	/**
	 * 捕获自定义异常(继承RuntimeException)，返回json数据同时回滚事物
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(MyException.class)
	public JsonResult myExceptionHandler(MyException e) {
		return new JsonResult(false, e.getCode(), e.getMsg());
	}
	
	/**
	 * 捕获RuntimeException
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(RuntimeException.class)
	public JsonResult runtimeExceptionHandler(RuntimeException e) {
		e.printStackTrace();
		return new JsonResult(false, ResultCode.RUNTIME_ERROR.getCode(),ResultCode.RUNTIME_ERROR.getMessage());
	}

	/**
	 * 捕获Exception
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public JsonResult exceptionHandler(Exception e) {
		e.printStackTrace();
		return new JsonResult(false, ResultCode.ERROR.getCode(),ResultCode.ERROR.getMessage());
	}

}
