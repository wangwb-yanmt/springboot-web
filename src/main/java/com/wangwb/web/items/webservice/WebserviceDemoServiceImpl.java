package com.wangwb.web.items.webservice;

import javax.jws.WebService;

import org.springframework.web.bind.annotation.RestController;

@WebService
public class WebserviceDemoServiceImpl implements WebserviceDemoService {

	@Override
	public String sayHello(String text) {
		return "你好："+text;
	}
	
	@Override
	public String sayHello2(String text) {
		return "你好："+text;
	}

}
