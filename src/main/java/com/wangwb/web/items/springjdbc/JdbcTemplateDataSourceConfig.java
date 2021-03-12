package com.wangwb.web.items.springjdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *	多数据源JdbcTemplate配置
 * @author wangwb@sparknet.com.cn
 *
 */
@Configuration
public class JdbcTemplateDataSourceConfig {

	/**
	 * 	返回数据源1的jdbcTemplate
	 * @param dataSource
	 * @return
	 */
	@Primary
	@Bean("jdbcTemplate1")
	public JdbcTemplate jdbcTemplate1(@Qualifier("dataSourceOne") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
	/**
	 * 	返回数据源2的jdbcTemplate
	 * @param dataSource
	 * @return
	 */
	@Bean("jdbcTemplate2")
	public JdbcTemplate jdbcTemplate2(@Qualifier("dataSourceTwo") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
}
