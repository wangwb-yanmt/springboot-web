package com.wangwb.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.wangwb.web.common.bean.UserBean;
import com.wangwb.web.common.util.StringUtil;
import com.wangwb.web.dao.LoginDao;

@Service
public class LoginService {
	
	@Resource
	private LoginDao loginDao;

	/**
	 * 	系统登录
	 * @param paramsMap
	 * @param session
	 * @return
	 */
	public Map<String, Object> login(Map<String, Object> paramsMap, HttpSession session) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
    	
    	String userName = StringUtil.nullToEmpty( paramsMap.get( "userName" ) );//用户名
    	String password = StringUtil.nullToEmpty( paramsMap.get( "password" ) );//密码
    	try{
    		List<Map<String, Object>> user = loginDao.queryUser(userName);
        	if(user.size() == 0){
        		resultMap.put("msg", "登陆失败!用户不存在，请检查是否正确");
        		resultMap.put("success",false);
        	}else{
        		if(password.equals(StringUtil.nullToEmpty(user.get(0).get("REG_NO")))){
        			UserBean userInfo = new UserBean();
        			String userId = StringUtil.nullToEmpty(user.get(0).get("ID"));//用户ID
        			userInfo.setUserId(userId);
        			session.setAttribute("sessionBean", userInfo);
        			resultMap.put("msg", "登陆成功");
                	resultMap.put("success",true);
        		}else{
        			resultMap.put("success",false);
        			resultMap.put("msg", "登陆失败!密码错误，请检查密码是否正确");
        		}
        	}
    	}catch(Exception e){
        	resultMap.put("success",false);
        	resultMap.put("msg", "请求数据发生异常");
        	throw new RuntimeException(e.getMessage(),e);
        }
    	return resultMap;
	}

	
	
}
