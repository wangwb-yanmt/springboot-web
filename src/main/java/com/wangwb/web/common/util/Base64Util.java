package com.wangwb.web.common.util;

import java.io.InputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64加密与解密
 * @author chenxy
 *
 */
@SuppressWarnings ( "restriction" )
public final class Base64Util {
	
	private Base64Util(){
	}
	
	
	/**
	 * 加密
	 * @param b
	 * @return
	 * @throws Exception
	 */
	public static String encode(byte[] b) throws Exception {
		return new BASE64Encoder().encode(b);
	}
	
	/**
	 * 加密
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String encode(String content) throws Exception {
		return encode(content.getBytes());
	}
	
	/**
	 * 加密
	 * @param content
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String encode(String content,String charset) throws Exception {
		return encode(content.getBytes(charset));
	}

	/**
	 * 解密
	 * @param content
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String decode(String content,String charset) throws Exception {
		byte[] buffer = new BASE64Decoder().decodeBuffer(content);
		return new String(buffer, charset);
	}
	
	/**
	 * 解密
	 * @param is
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String decode(InputStream is,String charset) throws Exception {
		byte[] buffer = new BASE64Decoder().decodeBuffer(is);
		return new String(buffer, charset);
	}
	
}
