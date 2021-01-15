package com.wangwb.web.common.bean;

public class SingletonTest {

	public static void main(String[] args) {
		Singleton aa = Singleton.getSingleton();
		aa.setName("wangwenbin");
		System.out.println(aa.getName());
		
		
		Singleton bb = Singleton.getSingleton();
		aa.setName("wangwenbin2");
		System.out.println(bb.getName());
		
		Singleton bb2 = Singleton.getSingleton();
		System.out.println(bb2.getName());

	}

}
