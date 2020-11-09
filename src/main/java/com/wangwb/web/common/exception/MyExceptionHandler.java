package com.wangwb.web.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 	全局异常处理
 * @author wangwb
 *
 */
@ControllerAdvice
public class MyExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);
	
	/**
	 * 捕获自定义异常(继承RuntimeException)
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(MyException.class)
	public Map<String, Object> myExceptionHandler(MyException e) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", e.getCode());
		resultMap.put("msg", e.getMsg());
		return resultMap;
	}

	/**
	 * 捕获异常
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Map<String, Object> exceptionHandler(Exception e) {
		log.error(e.getMessage());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		resultMap.put("msg", "发生异常");
		return resultMap;
	}

}
