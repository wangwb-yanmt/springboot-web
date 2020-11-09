package com.wangwb.web.common.util;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletResponse;


/**
 * 文件操作(写入、下载、删除文件和目录、文件MD5)
 * @author chenxy
 * 
 */

public final class FileUtil {
	
	private FileUtil(){
	}

	/**
	 * 文件写入
	 * @param input
	 * @param targetFile
	 * @throws IOException
	 */
	public static boolean writeFile(InputStream input, File targetFile) throws Exception {
		boolean returnBool=false;
		OutputStream output = null;
		BufferedOutputStream buffOutput=null;
		try{
			File parentFile = targetFile.getParentFile();
			if (!parentFile.exists() && !parentFile.mkdirs()){
				throw new IOException("无法创建目录:" + parentFile.getPath());
			}
			output = new FileOutputStream(targetFile);
			buffOutput = new BufferedOutputStream(output);
			int bufferSize=InputStreamUtil.getBufferSize(input);
			byte b[] = new byte[bufferSize];
			int len;
			if(null!=input){
				while ((len = input.read(b)) > 0){
					buffOutput.write(b, 0, len);
				}
			}
			buffOutput.flush();
			returnBool=true;
		}catch(Exception e){
			returnBool=false;
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if (buffOutput != null){
				buffOutput.close();
				buffOutput=null;
			}
			if (output != null){
				output.flush();
				output.close();
				output=null;
			}
			if (input != null){
				input.close();
				input=null;
			}
		}
		return returnBool;
	}
	
	/**
	 * 文件下载
	 * @param input
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	public static void downloadFile(InputStream input, String fileName,HttpServletResponse response) throws Exception{
		
		//转换2次 防止文件名过长被截断
		fileName=URLEncoder.encode(fileName,"GB2312"); 
		fileName=URLDecoder.decode(fileName, "ISO8859_1"); 

		OutputStream out = null;
        response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition","attachment; filename=\""+ fileName+ "\"");
		int bufferSize=InputStreamUtil.getBufferSize(input);
        byte[] buf = new byte[bufferSize];
        int len = 0;
        BufferedInputStream binput = null;
        try {
           binput = new BufferedInputStream(input);
           out = response.getOutputStream();
           while ((len = binput.read(buf)) > 0) {
        	   out.write(buf, 0, len);
           }
        }catch(Exception e){
        	throw new RuntimeException(e.getMessage(),e);
        }finally{
        	if(out!=null){
        		out.flush();
            	out.close();
            	out=null;
        	}
        	if(binput!=null){
        		binput.close();
        		binput=null;
        	}
        	if(input!=null){
        		input.close();
        		input=null;
        	}
        }
	}
	
	/**
	 * 删除文件
	 * @param f
	 * @throws IOException
	 */
	public static void delDirectory(File f) throws IOException {
		if (f.isDirectory()) {
			if (f.listFiles().length == 0) {
				if (!f.delete()){
					throw new IOException("删除失败!");
				}
			} else {
				File delFile[] = f.listFiles();
				int i = delFile.length;
				for (int j = 0; j < i; j++){
					delDirectory(delFile[j]);
				}
				if (!f.delete()){
					throw new IOException("删除失败!");
				}
			}
		} else if (!f.delete()){
			throw new IOException("删除失败!");
		}
	}

	/**
	 * 删除指定文件夹
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {  
	    try {  
	        delAllFile(folderPath); // 删除完里面所有内容   
	        File myFilePath = new File(folderPath.toString());  
	        if(null!=myFilePath){
	        	myFilePath.delete(); // 删除空文件夹   
	        }
	    } catch (Exception e) {  
	    	throw new RuntimeException(e.getMessage(),e);
	    }  
	}  

	/**
	 * 删除指定文件夹下所有文件
	 * @param path
	 * @return
	 */
	public static boolean delAllFile(String path) throws Exception{  
	    boolean flag = false;  
	    File file = new File(path);  
	    if (!file.exists()) {  
	        return flag;  
	    }  
	    if (!file.isDirectory()) {  
	        return flag;  
	    }  
	    String[] tempList = file.list();  
	    File temp = null;  
	    if(null!=tempList){
		    for (int i = 0; i < tempList.length; i++) {  
		        if (path.endsWith(File.separator)) {  
		            temp = new File(path + tempList[i]);  
		        } else {  
		            temp = new File(path + File.separator + tempList[i]);  
		        }  
		        if (temp.isFile()) {  
		            temp.delete();  
		        }  
		        if (temp.isDirectory()) {  
		            delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件   
		            delFolder(path + "/" + tempList[i]);// 再删除空文件夹   
		            flag = true;  
		        }  
		    }
	    }
	    return flag;  
	} 

	/**
	 * 获取文件的MD5值
	 * @param f
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMD5(File f) throws IOException, NoSuchAlgorithmException{
		String s="";
		InputStream fis = null;
		try{
			if (!f.isFile()){
				throw new IOException("获取MD5值失败：无效的文件：" + f.getPath());
			}
			fis = new FileInputStream(f);
			byte buffer[] = new byte[fis.available()];
			int numRead = 0;
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			char hexChar[] = {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
				'a', 'b', 'c', 'd', 'e', 'f'
			};
			byte b[] = md5.digest();
			StringBuilder sb = new StringBuilder(b.length * 2);
			for (int i = 0; i < b.length; i++){
				sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
				sb.append(hexChar[b[i] & 0xf]);
			}
			s = sb.toString();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if (fis != null){
				fis.close();
				fis=null;
			}
		}
		return s;
	}
	
	/** 
    * 获得指定文件的byte数组 
    */  
    public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    }
}
