package com.wangwb.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 	系统登出
 * @author wangwb
 *
 */
@RestController
@RequestMapping("/LoginOutController")
public class LoginOutController {

	/**
	 *	注销/退出系统
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try{
			HttpSession session=request.getSession();
			if(null!=session){
				session.removeAttribute("sessionBean");
				session.invalidate();
			}
			response.sendRedirect(request.getContextPath()+"/public/login.html");
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
}
