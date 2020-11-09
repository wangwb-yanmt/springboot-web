package com.wangwb.web.common.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public interface WebserviceDemoService {

	public String sayHello(String text);
	
	@WebMethod
	public String sayHello2(String text);
	
}
