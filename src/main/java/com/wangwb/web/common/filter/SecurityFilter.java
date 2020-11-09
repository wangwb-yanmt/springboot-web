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

import com.wangwb.web.common.util.StringUtil;



/**
 * 	安全漏洞过滤器
 * @author wangwb
 *
 */
public class SecurityFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		//解决缺少跨帧脚本编制防御漏洞(表示页面可以在相同域名下的frame中展示)
		httpServletResponse.addHeader("X-Frame-Options","SAMEORIGIN");
		
		String referer = httpServletRequest.getHeader("Referer");//获取来源页地址
		
		String serverName = request.getServerName();//localhost
		if (null != referer && referer.indexOf(serverName) < 0) {
			return;
		}
		String url = httpServletRequest.getRequestURI();
		String path = httpServletRequest.getRequestURL()+"?"+httpServletRequest.getQueryString();

		// 校验是否存在脚本、SQL注入信息
		if (checkSQLInject(path, url)) {
			System.out.println("检测到非法URL：" + path);
			httpServletResponse.setStatus(400); 
			return;
		}
		chain.doFilter(httpServletRequest, httpServletResponse);
	}

	@Override
	public void destroy() {
	}
	
	/**
	 * 检查是否存在非法字符，防止js脚本注入
	 * @param str 被检查的字符串
	 * @return true-字符串中存在非法字符，false-不存在非法字符
	 */
	public boolean checkSQLInject(String str, String url) {
		if (StringUtil.isEmpty(str)) {
			return false;// 如果传入空串则认为不存在非法字符
		}

		// 判断黑名单
		String[] inj_stra = { "script", "master", "truncate", "declare","iframe", "onreadystatechange", "alert",
				"atestu", "xss", ";", "\"", "\\", "svg", "confirm", "prompt", "onload", "onmouseover", "onfocus", "onerror" };

		str = str.toLowerCase(); // sql不区分大小写

		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				System.out.println("检测到URL存在非法字符：" + inj_stra[i]);
				return true;
			}
		}
		return false;
	}

}
