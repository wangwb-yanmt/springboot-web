package com.wangwb.web.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	/**
	 * 获取系统日期
	 */
	public static final String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
	    Calendar cd = Calendar.getInstance();   
	    cd.setTime(new Date());
        return sdf.format(cd.getTime());
    }
	/**
	 * 获取系统时间
	 */
	public static final String getTime(){
    	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");   
	    Calendar cd = Calendar.getInstance();   
	    cd.setTime(new Date());
        return sdf.format(cd.getTime());
    }
	/**
	 * 获取系统日期时间
	 */
	public static final String getDateTime(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	    Calendar cd = Calendar.getInstance();
	    cd.setTime(new Date());
        return sdf.format(cd.getTime());
    }
	
	private static GregorianCalendar calendar = new GregorianCalendar();
	/**
	 * 获取年份
	 */
	public static final int getYear() {
		return calendar.get(Calendar.YEAR);
	}
	/**
	 * 获取月份
	 */
	public static final int getMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}
	/**
	 * 获取天
	 */
	public static final int getDay() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static final int getCurrentDay(){
		return calendar.get(Calendar.DATE);
	}
	/**
	 * 获取时
	 */
	public static final int getHours() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 获取分
	 */
	public static final int getMinutes() {
		return calendar.get(Calendar.MINUTE);
	}
	/**
	 * 获取秒
	 */
	public static final int getSeconds() {
		return calendar.get(Calendar.SECOND);
	}
	/**
	 * 获取毫秒
	 */
	public static final int getMilliSeconds() {
		return calendar.get(Calendar.MILLISECOND);
	}
	
	/**
	 * 获取时间戳
	 * @return
	 */
	public static final long getTimestamp() {
		//方式1 效率最高
		long currentTime=System.currentTimeMillis(); 
		//方式2
//		long currentTime=Calendar.getInstance().getTimeInMillis();  
		//方式3
//		long currentTime=new Date().getTime();
		return currentTime;
	}
	
	public static void main(String[] args) {
		String timestamp = getDate();
		System.out.println(timestamp);
	}
	
}
