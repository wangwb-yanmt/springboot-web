package com.wangwb.web.common.util;

import java.util.Random;
import java.util.UUID;

/**
 * UUID生成类(32位)
 * @author chenxy
 *
 */
public final class UUIDUtil {
	
	private UUIDUtil(){
	}
	
	/**
	 * 获取字符ID
	 * @return
	 */
	public static final String getNextValue() {
		String id=UUID.randomUUID().toString();
		return id.replaceAll("-", "");
	}
	
	/**
	 * 获取数字ID
	 * @return
	 */
	public static final String getNextIntValue() {
		int totalLength=32;
		Random random=new Random();
		StringBuffer num=new StringBuffer();
		String time=String.valueOf(System.currentTimeMillis());
		int timeLength=time.length();
		int cycleLength=totalLength-timeLength;
		for(int i=0;i<cycleLength;i++){
			num.append(random.nextInt(10));
		}
		return time+num.toString();
	}
	
}
