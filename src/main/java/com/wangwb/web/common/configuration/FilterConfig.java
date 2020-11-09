package com.wangwb.web.common.configuration;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.wangwb.web.common.filter.MyFilter;
import com.wangwb.web.common.filter.SessionFilter;



/**
 * filter配置类
 * @author wangwb
 *
 */
@Configuration
public class FilterConfig {

	/**
	 * filter注册
	 */
	@Bean
	public FilterRegistrationBean sessionFilterRegist() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter( new SessionFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(0);
        return registration;
    }
	
//	@Bean
//    public Filter myFilter() {
//        return new MyFilter();
//    }
//	/**
//	 * filter注册,可在filter获取应用上下文
//	 */
//	@Bean
//	public FilterRegistrationBean myFilterRegist() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new DelegatingFilterProxy("myFilter"));
//        registration.addUrlPatterns("/*");
//        registration.setOrder(1);
//        registration.setName("myFilter");
//        return registration;
//    }
}

