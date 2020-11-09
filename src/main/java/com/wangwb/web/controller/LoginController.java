package com.wangwb.web.controller;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangwb.web.common.bean.UserBean;
import com.wangwb.web.common.util.ParamUtil;
import com.wangwb.web.common.util.StringUtil;
import com.wangwb.web.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 	系统登陆
 * @author wangwb
 *
 */
@RestController
@RequestMapping("/LoginController")
@Api(tags = "登录模块")
public class LoginController {
	
	
	@Resource
	private LoginService loginService;
	
	
	/**
	 * 系统登录
	 */
	@ApiOperation(value = "登录系统")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "userName", value = "登录用户名", required = true),
        @ApiImplicitParam(name = "password", value = "登录密码", required = true)
	})
	@PostMapping("/login")
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//解决session会话标识未更新漏洞
		HttpSession session=request.getSession();
		if(null!=session){
			session.invalidate();
		}
		Cookie[] cookies=request.getCookies();
		if(null!=cookies){
			cookies[0].setMaxAge(0);
		}
		session=request.getSession(true);
		//接收前台的参数集
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
//        return loginService.login(paramsMap,session);
        
        
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String userName = StringUtil.nullToEmpty( paramsMap.get( "userName" ) );//用户名
    	String password = StringUtil.nullToEmpty( paramsMap.get( "password" ) );//密码
		if("wangwenbin".equals(userName) && "111111".equals(password)){
			UserBean userInfo = new UserBean();
			userInfo.setUserId("001");
			session.setAttribute("sessionBean", userInfo);
			resultMap.put("msg", "登陆成功");
			resultMap.put("success",true);
		}else{
			resultMap.put("success",false);
			resultMap.put("msg", "登陆失败!密码错误，请检查密码是否正确");
		}
		return resultMap;
	}
	
	
}
