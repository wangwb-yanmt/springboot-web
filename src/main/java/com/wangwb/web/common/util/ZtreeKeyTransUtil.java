package com.wangwb.web.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ztree转key名称
 * @author admin
 *
 */
public class ZtreeKeyTransUtil {
	
	public static List<Map<String,Object>> ZtreeKeyTrans(List<Map<String,Object>> dataList) {
		List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
		Map<String,Object> newMap = null;
    	for(Map<String, Object> map1:dataList) {
    		newMap = new HashMap<String, Object>();
    		newMap.put("id", map1.get("ID"));
    		newMap.put("pId", map1.get("PID"));
    		newMap.put("name", map1.get("NAME"));
    		if(map1.get("NOCHECK") != null){
    			newMap.put("nocheck", map1.get("NOCHECK"));
    		}
    		if(map1.get("IFPOINT") != null){
    			newMap.put("ifpoint", map1.get("IFPOINT"));
    		}
    		newList.add(newMap);
    	}
    	return newList;
	}

}
