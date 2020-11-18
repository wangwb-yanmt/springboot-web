package com.wangwb.web.common.filter;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * filter中request添加参数使用
 * @author wangwb@sparknet.com.cn
 *
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

	//新的参数map
	private Map<String , String[]> params = new HashMap<>() ;

	//构造
	public ParameterRequestWrapper(HttpServletRequest request) {
		super(request);
		this.params.putAll(request.getParameterMap());
    }
	//构造
	public ParameterRequestWrapper(HttpServletRequest request , Map<String , Object> extendParams){
		this(request);
		addAllParameters(extendParams) ;
    }

	@Override
	public String getParameter(String name) {
		String[] values = params.get(name) ;
		if (values == null){
			return null ;
		}
		return values[0] ;
    }

	@Override
	public String[] getParameterValues(String name){
		String[] values = params.get(name) ;
		if (values == null || values.length == 0){
			return null ;
		}
		return values ;
    }

    /**
     * 在获取所有的参数名,必须重写此方法，
     * 否则对象中参数值映射不上
     * @return
     */
    @Override
    public Enumeration<String> getParameterNames(){
        return new Vector(params.keySet()).elements() ;
    }

    public void addAllParameters(Map<String, Object> extendParams) {
        for (Map.Entry<String , Object> entry : extendParams.entrySet())
            addParameter(entry.getKey() , entry.getValue()) ;
    }

    public void addParameter(String key, Object value) {
        if (value != null){
            if (value instanceof String[])
                params.put(key , (String[])value) ;
            else if (value instanceof String)
                params.put(key , new String[]{(String) value}) ;
            else
                params.put(key , new String[]{String.valueOf(value)}) ;
        }
    }
	
}
