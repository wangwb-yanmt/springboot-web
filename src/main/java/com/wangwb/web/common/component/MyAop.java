package com.wangwb.web.common.component;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 	捕获异常记录应用日志
 * @author wangwb
 *
 */
@Component
@Aspect
public class MyAop {
	
	/**
	 * 以添加了AppLog注解的方法为切面
	 */
	@Pointcut("@annotation(com.wangwb.web.common.interfaces.MyInterface)")
    public void PoinCut(){}
	
	/**
	 * 抛出异常后
	 * @param point
	 * @param e
	 * @throws IllegalAccessException
	 */
	@AfterThrowing(value="PoinCut()",throwing="e")
	public void afterThrow(JoinPoint point,Exception e) throws IllegalAccessException{
		String className = point.getTarget().getClass().getName();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		String methodName = method.getName();
//		String methodName = point.getSignature().getName();
		String classPath = className+":"+methodName;
	}
	
	/**
	 * 执行前
	 * @param point
	 */
	@Before("PoinCut()")
	public void before(JoinPoint point) {
		String className = point.getTarget().getClass().getName();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		String methodName = method.getName();
//		String methodName = point.getSignature().getName();
		String classPath = className+":"+methodName;
	}
	
	/**
	 * 执行后
	 * @param point
	 */
	@After("PoinCut()")
	public void after(JoinPoint point) {
		String className = point.getTarget().getClass().getName();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		String methodName = method.getName();
//		String methodName = point.getSignature().getName();
		String classPath = className+":"+methodName;
	}
	
	
	

}
