package com.wangwb.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication	//springboot启动类注解
@EnableScheduling	//开启定时任务
@EnableTransactionManagement  //经测试默认是开启的
@ServletComponentScan
public class ServiceLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceLoginApplication.class, args);
	}
	
}

