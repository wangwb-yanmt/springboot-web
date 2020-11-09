package com.wangwb.web.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 	接受前台参数转为map集合
 * @author wangwb
 *
 */
public class ParamUtil {

	public static Map<String, Object> requestParamMap(HttpServletRequest request) {
		java.util.Iterator iter = request.getParameterMap().entrySet().iterator();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
        while (iter.hasNext()) {
            java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
            String key = StringUtil.strFiltrate(entry.getKey().toString());
            String[] checkboxValues = request.getParameterValues(key);
            String value = StringUtil.strFiltrate(request.getParameter(key)); // 这里能成功输出VAL值

            if(checkboxValues != null && checkboxValues.length>1){
            	String checkboxV = "";
            	for(int i = 0; i < checkboxValues.length ; i++){
            		checkboxV += checkboxValues[i]+",";
            	}
            	if(!"".equals(checkboxV)){
            		value = checkboxV.substring(0, checkboxV.length()-1);
            	}
            }
            
            map.put(key, StringUtil.strFiltrate(value)); //添加安全过滤，替换非法字符
        }
        return map;
	}
	
	
}

