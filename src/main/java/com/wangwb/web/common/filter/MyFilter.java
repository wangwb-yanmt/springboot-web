package com.wangwb.web.common.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.wangwb.web.service.TestService;


/**
 * 	自定义filter
 * @author wangwb
 *
 */
public class MyFilter implements Filter {
	
	@Autowired
	private TestService testService;
	
	@Override
	public void init(FilterConfig filterConfig) {
		
	}
	
	@Override
	public void destroy() {
		
	}
	
	//不需要过滤的url内容
	private static final String[] unNeedFilterUrlArray = {"/public","/LoginController"};
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		StringBuffer url = httpServletRequest.getRequestURL();
		String urlString = url.toString();
		System.out.println("请求的url："+urlString);
		String tokenid = "";
		
		Cookie[] cookies = httpServletRequest.getCookies();
		if(cookies != null && cookies.length > 0){
			for (Cookie cookie : cookies){
				if("tokenid".equals(cookie.getName())) {
					tokenid = cookie.getValue();
					break;
				}
			}
		} 
		chain.doFilter(servletRequest, servletResponse);
	}
	
	//判断请求的url是否需要过滤
	private boolean isNeedFilter(String requestUrl) {
		for (String unNeedFilterUrl : unNeedFilterUrlArray) {
            if(requestUrl.contains(unNeedFilterUrl)) {
                return false;
            }
        }
        return true;
	}
	

}
