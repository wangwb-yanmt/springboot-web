package com.wangwb.web.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;

import com.wangwb.web.common.bean.JsonResult;
import com.wangwb.web.common.myenum.ResultCode;
import com.wangwb.web.common.util.RedisUtil;

import net.sf.json.JSONObject;

public class TokenFilter implements Filter {
	
	@Resource
	private RedisUtil redisUtil;
	
	public FilterConfig filterConfig;
	
	//不需要验证token的url,作为后端服务，无需验证的登录接口，前后段一起时加上前端公开模块/public
	private static final String[] unNeedFilterUrlArray = {"/LoginController","/LoginOutController"};

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		response.setContentType("application/json;charset=utf-8");
		//filter设置跨域
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "*");
		response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
		
		String requestUrl = request.getRequestURI();
		boolean isNeedFilter = isNeedFilter(requestUrl);
		JsonResult jsonResult = null;
		//ajax跨域OPTIONS预请求直接跳过
		if(RequestMethod.OPTIONS.name().equals(request.getMethod())) {
			return;
		}else {
			if (!isNeedFilter) {
				//不需要过滤的url
				filterChain.doFilter(servletRequest, servletResponse);
			} else { 
				String token = request.getHeader("token");
				if("".equals(token) || token == null || "null".equals(token)) {
					token = request.getParameter("token");
				}
				System.out.println("后台接收到的token是："+token);
				if("".equals(token) || token == null || "null".equals(token)) {
					PrintWriter out = response.getWriter();
					jsonResult = new JsonResult(false,ResultCode.USER_NOT_LOGIN.getCode(),ResultCode.USER_NOT_LOGIN.getMessage());
					JSONObject jsonObject = JSONObject.fromObject(jsonResult);
					out.append(jsonObject.toString());
				} else {
					//判断token是否有效
					boolean isHasKey = redisUtil.hasKey("token:"+token);
					if(isHasKey) {
						//根据token取loginId
						String loginId =com.wangwb.web.common.util.StringUtil.nullToEmpty(redisUtil.get("token:"+token));
						//request添加参数
						ParameterRequestWrapper wrapper = new ParameterRequestWrapper(request);
						wrapper.addParameter("loginId",loginId);
	        			//并更新token
	        			redisUtil.expire("token:"+token, 7200);
	        			filterChain.doFilter(wrapper, servletResponse);
	        		} else {
	        			PrintWriter out = response.getWriter();
	        			jsonResult = new JsonResult(false,ResultCode.USER_ACCOUNT_EXPIRED.getCode(),ResultCode.USER_ACCOUNT_EXPIRED.getMessage());
	    				JSONObject jsonObject = JSONObject.fromObject(jsonResult);
	    				out.append(jsonObject.toString());
	        		}
				}
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
