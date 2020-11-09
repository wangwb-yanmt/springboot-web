package com.wangwb.web.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.wangwb.web.common.exception.MyException;

/**
 * 	获取resources下properties文件值
 * @author wangwb
 *
 */
public class PropertiesUtil {
	
	public static String getValue(String filePath,String key) {
		String value = "";
		try {
			InputStream in = PropertiesUtil.class.getResourceAsStream(filePath);
			Properties properties = new Properties();
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			properties.load(bf);
			value = properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			throw new MyException("获取配置文件属性异常");
		}
		return value;
	}
	
	public static void main(String[] args) {
		String aa = getValue("/fastdfs-client.properties", "fastdfs.http_secret_key");
		System.out.println(aa);
	}

}
