package com.wangwb.web.common.util;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 	这里用的是网建短信通
 * @author admin
 *
 */
public class SmsUtil {
	
	public static String sendSms(String smsMob,String smsText) throws HttpException, IOException {
		//创建HttpClient
		HttpClient client = new HttpClient();
		//创建POST请求
		PostMethod post = new PostMethod("http://gbk.api.smschinese.cn");
		//添加请求头
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		//设置请求参数
		NameValuePair[] data ={
				new NameValuePair("Uid", "wangwb"),//注册的用户名
				new NameValuePair("Key", "d41d8cd98f00b204e980"),//接口密钥
				new NameValuePair("smsMob",smsMob),//发送的目标号码
				new NameValuePair("smsText",smsText)//短信内容
		};
		post.setRequestBody(data);
		//执行请求
		client.executeMethod(post);
		
		/*//获取响应头组
		Header[] headers = post.getResponseHeaders();
		//获取状态值
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers) {
			System.out.println(h.toString());
		}*/
		//获取响应体
		String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
		System.out.println(result); //打印返回消息状态

		//释放请求连接
		post.releaseConnection();

		return result;
	}
	
	/**
     * 	随机生成6位验证码
     * @return
     */
    public static String getRandomCode(Integer code){
        Random random = new Random();
        StringBuffer result= new StringBuffer();
        for (int i=0;i<code;i++){
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
	

}
