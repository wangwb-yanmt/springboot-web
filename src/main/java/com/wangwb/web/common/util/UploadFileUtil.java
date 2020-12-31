package com.wangwb.web.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;

/**
 *	文件上传
 * @author wangwb
 *
 */
public class UploadFileUtil {

	/**
	 * 	通过transferTo上传
	 * @param file 文件流数据
	 * @param uploadPath 上传路径
	 * @return
	 */
	public static Map<String, Object> upload1(MultipartFile file, String uploadPath) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (file==null) {
			resultMap.put("success", "false");
			resultMap.put("msg", "请选择文件");
		}else {
        	//获取文件名
			String fileName = file.getOriginalFilename();
        	//创建目标文件
        	File dest = new File(uploadPath+fileName);
            //检测目标文件是否存在
        	if(!dest.getParentFile().exists()){
        		dest.getParentFile().mkdir();
    		}
            //该目标文件如果已存在则删除
        	if(dest.exists()) {
        		dest.delete();
            }
        	try {
            file.transferTo(dest);
            resultMap.put("success", true);
				resultMap.put("msg", "上传成功");
				resultMap.put("path", uploadPath + fileName);
				resultMap.put("fileName",fileName);
        	} catch (IOException e) {
	        	e.printStackTrace();
	        	resultMap.put("success", false);
				resultMap.put("msg", "出现异常");
            }
        }
		return resultMap;
	}
	
	/**
	 * 	通过流的方式上传
	 * @param file 文件流数据
	 * @param uploadPath 上传路径
	 * @return
	 * @throws IOException 
	 */
	public static Map<String, Object> upload2(MultipartFile file, String uploadPath) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (file.isEmpty()) {
			resultMap.put("success", "false");
			resultMap.put("msg", "请选择文件");
		}else {
        	//文件名
        	String fileName = file.getOriginalFilename();
        	try {
				//文件流
				InputStream is = file.getInputStream();
				//定义输出流
				OutputStream opStream = new FileOutputStream(uploadPath+fileName);
				int temp;
				//读文件流并返回读取的字节
				while((temp=is.read())!=(-1)) {
					//字节写入到输出流
					opStream.write(temp);
				}
				opStream.flush();
				opStream.close();
				is.close();
				
				resultMap.put("success", "true");
    			resultMap.put("msg", "上传成功");
    			resultMap.put("path", uploadPath + fileName);
    			resultMap.put("fileName",fileName);
			} catch (Exception e) {
				resultMap.put("success", "false");
    			resultMap.put("msg", "出现异常");
				e.printStackTrace();
			}
        }
		return resultMap;
	}
	
	/**
	 * 	通过transferTo上传  IE8
	 * @param file 文件流数据
	 * @param uploadPath 上传路径
	 * @return
	 */
	public static void uploadIE8(MultipartFile file, String uploadPath, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		JSONObject object = new JSONObject();
		try {
			writer = response.getWriter();
			if (file==null) {
				object.put("success", false);
				object.put("msg", "上传失败，请选择文件");
			}
			int limitSize = 3*1024*1024;
			if(file.getSize() > limitSize) {
				object.put("success", false);
				object.put("msg", "上传失败，文件大小需要限制在3M之内");
			}
	    	//获取文件名
			String fileName = file.getOriginalFilename();
	    	//创建目标文件
			String uuid = UUIDUtil.getNextValue();
			String projectPath = uploadPath + uuid + "/";
	    	File dest = new File(projectPath+fileName);
	    	//该目标文件如果已存在则删除
	    	if(dest.exists()) {
	    		dest.delete();
	        }
	        //检测目标文件是否存在
	    	if (!dest.exists()) {
	    		dest.mkdirs();
			}
	    	file.transferTo(dest);
	    	object.put("success", true);
	    	object.put("msg", "上传成功");
	    	object.put("path", projectPath+fileName);
	    	object.put("fileName",fileName);
		} catch (IOException e1) {
			object.put("success", false);
			object.put("msg", "上传出现异常");
		}finally {
			writer.print(object);
			writer.flush();
			writer.close();
		}
	}
	
}
