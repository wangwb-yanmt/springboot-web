package com.wangwb.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wangwb.web.common.util.UploadFileUtil;

/**
 *	文件上传示例
 * @author wangwb
 *
 */
@RestController
@RequestMapping("/UploadController")
public class UploadController {

	/**
	 * 	通过transferTo上传
	 * @param file
	 * @return
	 */
	@RequestMapping("/upload1")
	public Map<String, Object> upload1(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println("文件名："+fileName);
		resultMap = UploadFileUtil.upload1(file, "/home/wangwb@sparknet.com.cn/桌面/upload/");
		return resultMap;
	}
	
	
	
	
}
