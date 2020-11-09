package com.wangwb.web.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 下载文件
 * @author wangwb@sparknet.com.cn
 *
 */
public class DownloadFileUtil {
	
	private static final Logger log = LoggerFactory.getLogger(DownloadFileUtil.class);
	
	/**
	 * 下载文件
	 * @param filePath  服务器中文件路径
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public static void downloadFile(String filePath, HttpServletResponse response) throws Exception {
		File file = new File(filePath);
		String fileName = file.getName();
//		设置返回类型为二进制流（不知道文件类型）
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition","attachment; filename=\""+ fileName+ "\"");
		try {
			//转换2次 防止文件名过长被截断
			fileName=URLEncoder.encode(fileName,"GB2312"); 
			fileName=URLDecoder.decode(fileName, "ISO8859_1"); 
			//读取文件
			InputStream inputStream = new FileInputStream(file);
			//转为缓存流
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			//缓存大小
			int bufferSize = InputStreamUtil.getBufferSize(inputStream);
			//定义缓存大小的字节数组
			byte[] buf = new byte[bufferSize];
			//定义输出流
			OutputStream outputStream = response.getOutputStream();
			int len = 0;
			//读入到数组，返回读入的字节数量
			while ((len = bufferedInputStream.read(buf)) > 0) {
				//将数组中的字节写入到输出流
				outputStream.write(buf, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			bufferedInputStream.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			log.error("下载文件出现异常");
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	/**
	 * 下载文件
	 * @param inputStream  文件二进制流
	 * @param fileName  文件名
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public static void downloadFile(InputStream inputStream, String fileName, HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition","attachment; filename=\""+ fileName+ "\"");
		try {
			//转换2次 防止文件名过长被截断
			fileName=URLEncoder.encode(fileName,"GB2312"); 
			fileName=URLDecoder.decode(fileName, "ISO8859_1"); 
			//转为缓存流
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			//缓存大小
			int bufferSize = InputStreamUtil.getBufferSize(inputStream);
			//定义缓存大小的字节数组
			byte[] buf = new byte[bufferSize];
			//定义输出流
			OutputStream outputStream = response.getOutputStream();
			int len = 0;
			//读入到数组，返回读入的字节数量
			while ((len = bufferedInputStream.read(buf)) > 0) {
				//数组写入到输出流
				outputStream.write(buf, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			bufferedInputStream.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			log.error("下载文件出现异常");
			throw new RuntimeException(e.getMessage(),e);
		}
	}

}
