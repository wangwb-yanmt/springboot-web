package com.wangwb.web.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import net.bytebuddy.asm.Advice.This;

public class NewDateUtil {
	
	//当前日期时间实例
	LocalDateTime localDateTime = LocalDateTime.now();
	//当前日期实例
	LocalDate localDate =LocalDate.now();
	//当前时间实例
	LocalTime localTime = LocalTime.now();
	//当前年
	int year = localDateTime.getYear();//localDate.getYear();
	//当前月
	int month = localDateTime.getMonthValue();//localDate.getMonthValue();
	//当前日
	int day = localDateTime.getDayOfMonth();//localDate.getDayOfMonth();
	//当前时
	int hour = localDateTime.getHour();//localTime.getHour();
	//当前分
	int minute = localDateTime.getMinute();
	//当前秒
	int second = localDateTime.getSecond();
	//当前纳秒
	int nanoSecond = localDateTime.getNano();
   
    //1年后
    LocalDateTime newDateTime1 = localDateTime.plusYears(1);
    //1月后
    LocalDateTime newDateTime2 = localDateTime.plusMonths(1);
    //1周后
    LocalDateTime newDateTime3 = localDateTime.plusWeeks(1);
    //1天后
    LocalDateTime newDateTime4 = localDateTime.plusDays(1);
    //1小时后
    LocalDateTime newDateTime5 = localDateTime.plusHours(1);
    //1分钟后
    LocalDateTime newDateTime6 = localDateTime.plusMinutes(1);
    //1秒钟后
    LocalDateTime newDateTime7 = localDateTime.plusSeconds(1);
    
    //1年前
    LocalDateTime newDateTime8 = localDateTime.minusYears(1);
    //1月前
    LocalDateTime newDateTime9 = localDateTime.minusMonths(1);
    //1周前
    LocalDateTime newDateTime10 = localDateTime.minusWeeks(1);
    //1天前
    LocalDateTime newDateTime11 = localDateTime.minusDays(1);
    //1小时前
    LocalDateTime newDateTime12 = localDateTime.minusHours(1);
    //1分钟前
    LocalDateTime newDateTime13 = localDateTime.minusMinutes(1);
    //1秒钟前
    LocalDateTime newDateTime14 = localDateTime.minusSeconds(1);
    
    //1年中的第5天（from 1 to 365-366）
    LocalDateTime newDateTime15 = localDateTime.withDayOfYear(5);
    //1月中的第5天（from 1 to 28-31）
    LocalDateTime newDateTime16 = localDateTime.withDayOfMonth(5);
    
    //日期格式化
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD HH24:MI:SS");
    //日期to字符串
    String dateTimeStr = localDateTime.format(formatter);
    
    //字符串to日期
    LocalDateTime newDateTime17 = LocalDateTime.parse("2017-09-28 17:07:05",formatter);
    
    
    
    //手动创建实例
    LocalDate date = LocalDate.of(2020, 4, 21);
    LocalTime time = LocalTime.of(11, 30, 30, 0);
    LocalDateTime date2 = LocalDateTime.of(date, time);
    
    /**
     * 获取当前日期时间字符串
     * @return
     */
	public static String getDateTime() {
		//当前日期时间实例
    	LocalDateTime localDateTime = LocalDateTime.now();
    	//日期格式化
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	String dateTimeStr = localDateTime.format(formatter);
    	return dateTimeStr;
    }
	
	/**
     * 获取当前日期字符串
     * @return
     */
	public static String getDate() {
		//当前日期实例
		LocalDate localDate =LocalDate.now();
    	//日期格式化
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    	String dateStr = localDate.format(formatter);
    	return dateStr;
    }
	
	/**
     * 获取当前时间字符串
     * @return
     */
	public static String getTime() {
		//当前日期实例
		LocalTime localTime = LocalTime.now();
    	//日期格式化
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    	String timeStr = localTime.format(formatter);
    	return timeStr;
    }
	
	public static void main(String[] args) {
		String dateTime = getDateTime();
		String date = getDate();
		String time = getTime();
		System.out.println("日期时间："+dateTime);
		System.out.println("日期："+date);
		System.out.println("时间："+time);
	}

}
