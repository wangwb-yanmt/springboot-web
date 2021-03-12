package com.wangwb.web.items.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 	RestTemplate配置类，一般不用任何配置也可使用
 * @author wangwb
 *
 */
@Configuration
public class RestTemplateConfig {
	
	@Bean
	public RestTemplate registerTemplate() {
		RestTemplate restTemplate = new RestTemplate(getFactory());
		return restTemplate;
	}
	
	/**
	 * 	初始化请求工厂
	 * @return
	 */
	private SimpleClientHttpRequestFactory getFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //设置建立连接的超时时间
		factory.setConnectTimeout(3600);
        //设置传递数据的超时时间
		factory.setReadTimeout(3600);
		return factory;
    }

}
