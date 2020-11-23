package com.wangwb.web.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 	WebMvc配置
 * @author wangwb
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	/**
	 * 允许跨与请求
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    // 设置允许跨域的路径
		registry.addMapping("/**")
		// 设置允许跨域请求的域名
		.allowedOrigins("*")
		// 是否允许证书 不再默认开启
		.allowCredentials(true)
		// 设置允许的方法
		.allowedMethods("*")
		.allowedHeaders("*")
		// 跨域允许时间
		.maxAge(3600);
	}
}
