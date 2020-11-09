package com.wangwb.web.common.util;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * XML工具类
 * @author wangwb@sparknet.com.cn
 *
 */
public class XmlUtil {
	
	/**
	 * 得到文档对象
	 * @param request
	 * @return
	 */
	public static Document getDoc(HttpServletRequest request) {
		ServletInputStream sis = null;
		String xmlData = "";
		Document doc = null;
		try {
			request.setCharacterEncoding("UTF-8");
			sis = request.getInputStream();
			int size = request.getContentLength();  
            // 用于缓存每次读取的数据  
			byte[] buffer = new byte[size];  
            // 用于存放结果的数组  
			byte[] xmldataByte = new byte[size];  
			int count = 0;  
			int rbyte = 0;  
            // 循环读取  
			while (count < size) {   
				// 每次实际读取长度存于rbyte中  
				rbyte = sis.read(buffer);   
				for(int i=0;i<rbyte;i++) {  
					xmldataByte[count + i] = buffer[i];  
                }  
				count += rbyte;  
            }  
			xmlData = new String(xmldataByte, "UTF-8"); 
			if (xmlData != null) {
				doc = DomUtil.parseXMLDocument(xmlData);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			if(sis!=null) {
				try {
					sis.close();
					sis = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		return doc;
	}
	
	/**
	 * 获取根节点
	 * @param doc
	 * @return
	 */
	public static Element getRootNode(Document doc) {
		if (doc != null) {
			return (Element) doc.getFirstChild();
		}
		return null;
	}
	
	/**
	 * 根据节点名称获取节点
	 * @param path
	 * @return NodeList
	 */
	public static NodeList getXmlNode(Document doc, String elementName) {
		NodeList nodeList = null;
		if (elementName != null) {
			nodeList = doc.getElementsByTagName(elementName);
		}
		return nodeList;
	}
	
	/**
	 * 获取节点下的map
	 * @param element 节点
	 * @param elementName
	 * @return
	 */
	public Map<String, Object> getNodeMap(Element element) {
		Map<String, Object> map = new HashMap<String, Object>();
		NodeList nodeList = element.getChildNodes();
		for (int i=0;i<nodeList.getLength();i++) {
			map.put(nodeList.item(i).getNodeName().trim(), nodeList.item(i).getTextContent().trim());
		}
		return map;
	}

}
