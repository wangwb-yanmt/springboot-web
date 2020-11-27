package com.wangwb.web.somelearn;

import java.util.ArrayList;
import java.util.List;

/**
 * jdk1.8 stream api test
 * @author wangwb@sparknet.com.cn
 *
 */
public class StreamTest {

	public static void main(String[] args) {
		
		//模拟列表数据
		List<String> tempList = new ArrayList<String>();
		tempList.add("wangwb1");
		tempList.add("wangwb2");
		tempList.add("wangwb3");
		tempList.add("wangwb4");
		tempList.add("wangwb5");
		tempList.add("wangwb6");
		tempList.add("wangwb6");
		tempList.add("wangwb8");
		tempList.add("wangwb9");
		tempList.add("wangwb10");
		
		tempList.stream().filter((name) -> name.equals("wangwb6")).forEach(System.out::println);
		
		long count1 = tempList.stream().filter(name -> name.equals("wangwb6")).count();
		System.out.println(count1);
		
		long count2 = tempList.stream().filter(name -> name.equals("wangwb6")).distinct().count();
		System.out.println(count2);
		
		boolean flag1 = tempList.stream().allMatch(name -> name.equals("wangwb6"));
		System.out.println(flag1);
		
		boolean flag2 = tempList.stream().anyMatch(name -> name.equals("wangwb6"));
		System.out.println(flag2);
	}
	
}
