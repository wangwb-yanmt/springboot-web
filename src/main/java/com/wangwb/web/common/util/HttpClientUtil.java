package com.wangwb.web.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 	远程调用
 * @author wangwb
 *
 */
public class HttpClientUtil {
	
	/**
	 * get请求
	 * @param args
	 * @throws Exception
	 */
	public String getRequest(String url) throws Exception {
		//创建HttpClient对象
	    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	    //创建HttpGet对象
	    HttpGet httpGet = new HttpGet(url);
	    //创建response
	    CloseableHttpResponse response = null;
	    
	    //执行GET请求
        response = httpClient.execute(httpGet);
        //获取响应实体
        HttpEntity entity = response.getEntity();
        //释放资源
        response.close();
        httpClient.close();
        return EntityUtils.toString(entity);
	}
	
	/**
	 * post请求
	 * @param args
	 * @throws Exception
	 */
	public String postRequest(String url,Map<String, String> paramsMap) throws Exception {
		//创建HttpClient对象
	    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	    //创建HttpPost对象
	    HttpPost httpPost = new HttpPost(url);
	    //设置请求参数
	    List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
	    for(Map.Entry<String, String> tempEntry:paramsMap.entrySet()) {
	    	params.add(new BasicNameValuePair(tempEntry.getKey(), tempEntry.getValue()));
	    }
	    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params);
	    httpPost.setEntity(urlEncodedFormEntity);
	    //执行请求，返回response
	    CloseableHttpResponse response = httpClient.execute(httpPost);
	    
        //获取响应实体
        HttpEntity entity = response.getEntity();
        //释放资源
        response.close();
        httpClient.close();
        return EntityUtils.toString(entity);
	}

}
