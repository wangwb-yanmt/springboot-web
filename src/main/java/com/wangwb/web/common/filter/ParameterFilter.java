package com.wangwb.web.common.filter;

import java.io.IOException;
import java.util.Enumeration;

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
 * 参数过滤器（跨站点脚本编制）
 * @author wangwb
 *
 */
public class ParameterFilter implements Filter{
	

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse response = (HttpServletResponse) res;
		// 获取请求所有参数，校验防止脚本注入，防止XSS漏洞
		String url = req.getRequestURI();
		Enumeration<?> params = req.getParameterNames();
		String paramN = null;
		while (params.hasMoreElements()) {
			paramN = (String) params.nextElement();
			String paramVale = req.getParameter(paramN);
			if (!paramN.toLowerCase().contains("password")) {
				//System.out.println("传参为：" + paramN + "==" + paramVale);
			}
			// 校验是否存在脚本、SQL注入信息
			if (checkSQLInject(paramVale, url)) {
				System.out.println("检测到非法传参URL：" + paramN + "==" + paramVale);
				response.setStatus(400); 
				return;
			}
		}
		chain.doFilter(req, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@SuppressWarnings ("unused")
	private String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '>':
				sb.append('＞');// 全角大于号
				break;
			case '<':
				sb.append('＜');// 全角小于号
				break;
			case '\'':
				sb.append('‘');// 全角单引号
				break;
			case '\"':
				sb.append('“');// 全角双引号
				break;
			case '&':
				sb.append('＆');// 全角
				break;
			case '\\':
				sb.append('＼');// 全角斜线
				break;
			case '#':
				sb.append('＃');// 全角井号
				break;
			case '(':
				sb.append('（');//
				break;
			case ')':
				sb.append('）');//
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
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
				"atestu", "xss", ";", "svg", "confirm", "prompt", "onload", "onmouseover", "onfocus", "onerror" };

		str = str.toLowerCase(); // sql不区分大小写

		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				System.out.println("检测到非法参数值：" + str + "包含" + inj_stra[i]);
				return true;
			}
		}
		return false;
	}
	
}
