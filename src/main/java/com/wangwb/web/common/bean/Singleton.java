package com.wangwb.web.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 单例双重校验实现
 * @author wangwb@sparknet.com.cn
 *
 */
public class Singleton {
	
	private  String name;

	private volatile static Singleton singleton;
	
	private Singleton (){} 
	
	public static Singleton getSingleton() {  
		if (singleton == null) {  
			synchronized (Singleton.class) {  
				if (singleton == null) {  
					singleton = new Singleton();  
				}  
			}  
		}  
		return singleton;  
	}

	public  String getName() {
		return singleton.name;
	}

	public  void setName(String name) {
		singleton.name = name;
	}
	
}
