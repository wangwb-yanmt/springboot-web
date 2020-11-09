package com.wangwb.web.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 	stream string byte[]转换
 * @author chenxy
 * 
 */
public final class InputStreamUtil {
	
	private InputStreamUtil(){
	}
	
	private final static int BUFFER_SIZE = 4096;

	/**
	 * 	将InputStream转换成String
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static String InputStreamToString(InputStream is) throws Exception {
		String str="";
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			int bufferSize=getBufferSize(is);
			byte[] data = new byte[bufferSize];
			int count = -1;
			while ((count = is.read(data, 0, bufferSize)) != -1){
				os.write(data, 0, count);
			}
			data = null;
			str=new String(os.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(null!=os){
				os.flush();
				os.close();
				os=null;
			}
			if(null!=is){
				is.close();
				is=null;
			}
		}
		return str;
	}

	/**
	 * 	将InputStream转换成某种字符编码的String
	 * @param is
	 * @param encoding
	 * @return String
	 * @throws Exception
	 */
	public static String InputStreamToString(InputStream is, String encoding) throws Exception {
		String str="";
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			int bufferSize=getBufferSize(is);
			byte[] data = new byte[bufferSize];
			int count = -1;
			while ((count = is.read(data, 0, bufferSize)) != -1){
				os.write(data, 0, count);
			}
			data = null;
			str=new String(os.toByteArray(), encoding);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		} finally{
			if(null!=os){
				os.close();
				os=null;
			}
			if(null!=is){
				is.close();
				is=null;
			}
		}
		
		return str;
	}

	/**
	 * 	将String转换成InputStream
	 * @param in
	 * @return InputStream
	 * @throws Exception
	 */
	public static InputStream StringToInputStream(String str) throws Exception {
		return new ByteArrayInputStream(str.getBytes());
	}
	
	/**
	 * 	将String转换成某种字符编码InputStream
	 * @param str
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static InputStream StringToInputStream(String str, String encoding) throws Exception {
		return new ByteArrayInputStream(str.getBytes(encoding));
	}

	/**
	 * 	将InputStream转换成byte数组
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] InputStreamToByte(InputStream is) throws IOException {
		byte[] bytes=null;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			int bufferSize=getBufferSize(is);
			byte[] data = new byte[bufferSize];
			int count = -1;
			while ((count = is.read(data, 0, bufferSize)) != -1){
				os.write(data, 0, count);
			}
			data = null;
			bytes=os.toByteArray();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(null!=os){
				os.close();
				os=null;
			}
			if(null!=is){
				is.close();
				is=null;
			}
		}
		return bytes;
	}

	/**
	 * 	将byte数组转换成InputStream
	 * @param in
	 * @return InputStream
	 * @throws Exception
	 */
	public static InputStream byteToInputStream(byte[] b) throws Exception {
		return new ByteArrayInputStream(b);
	}

	/**
	 * 	将byte数组转换成String
	 * @param in
	 * @return String
	 * @throws Exception
	 */
	public static String byteToString(byte[] b) throws Exception {
		String str="";
		InputStream is = null;
		try{
			is = byteToInputStream(b);
			str=InputStreamToString(is);
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(null!=is){
				is.close();
				is=null;
			}
		}
		return str;
	}
	
	/**
	 * 	获取文件流的Buffer Size
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static int getBufferSize(InputStream is) throws Exception {
		int bufferSize=is.available();
		if(bufferSize==0){
			return BUFFER_SIZE;
		}else{
			return bufferSize;
		}
	}

}
