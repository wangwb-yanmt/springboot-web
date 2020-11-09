package com.wangwb.web.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 	获取resources下properties文件值
 * @author wangwb
 *
 */
public class PropertiesUtil {
	
	public static String getValue(String filePath,String key) throws IOException {
		InputStream in = PropertiesUtil.class.getResourceAsStream(filePath);
		Properties properties = new Properties();
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		properties.load(bf);
		String value = properties.getProperty(key);
		return value;
	}

}
