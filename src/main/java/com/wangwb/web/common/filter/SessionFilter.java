package com.wangwb.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 	session过滤器
 * @author wangwb
 *
 */
public class SessionFilter implements Filter{
	
	public FilterConfig filterConfig;
	
	//不需要过滤的url内容
	private static final String[] unNeedFilterUrlArray = {"/public","/LoginController","/GeetestController"};

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		
		httpServletResponse.addHeader("X-Frame-Options","SAMEORIGIN");
		
		HttpSession session = httpServletRequest.getSession(false);
		
		String requestUrl = httpServletRequest.getRequestURI();
		boolean isNeedFilter = isNeedFilter(requestUrl);
		
		if (!isNeedFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            // session中包含sessionBean对象,则是登录状态
        	if(session!=null&&session.getAttribute("sessionBean") != null){
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                //由后台重定向到登录页
            	httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/public/login.html");
            }
        }
	}
	
	@Override
	public void destroy() {
		this.filterConfig = null;
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
