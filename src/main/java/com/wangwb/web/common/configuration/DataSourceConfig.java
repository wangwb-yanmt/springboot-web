package com.wangwb.web.common.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

@Configuration
public class DataSourceConfig {
	
	@Primary
	@Bean(value = "dataSourceOne",initMethod = "init")
	@ConfigurationProperties("spring.datasource.druid.one")
	public DataSource dataSourceOne() {
		return DruidDataSourceBuilder.create().build();
	}
	
	@Bean(value = "dataSourceTwo",initMethod = "init")
	@ConfigurationProperties("spring.datasource.druid.two")
	public DataSource dataSourceTwo() {
		return DruidDataSourceBuilder.create().build();
	}
	

}
