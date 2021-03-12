package com.wangwb.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangwb.web.common.bean.JsonResult;
import com.wangwb.web.common.myenum.ResultCode;
import com.wangwb.web.common.util.RedisUtil;
import com.wangwb.web.common.util.StringUtil;
import com.wangwb.web.items.redis.MyRedisTemplate;

/**
 * 	系统登出
 * @author wangwb
 *
 */
@RestController
@RequestMapping("/LoginOutController")
public class LoginOutController {
	
	@Autowired
	private MyRedisTemplate redisUtil;

	/**
	 *	注销/退出系统 session模式
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response) {
    	try{
			HttpSession session=request.getSession();
			if(null!=session){
				session.removeAttribute("sessionBean");
				session.invalidate();
			}
			response.sendRedirect(request.getContextPath()+"/public/login.html");
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
    
    /**
	 *	注销/退出系统 前后段分离token模式
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/loginOutByToken")
    public JsonResult loginOutByToken(HttpServletRequest request, HttpServletResponse response) {
    	JsonResult jsonResult = new JsonResult();
    	String token = StringUtil.nullToEmpty(request.getHeader("token"));
    	try {
			//删除redis中key
			redisUtil.del("token:"+token);
			jsonResult.setSuccess(true);
			jsonResult.setCode(ResultCode.SUCCESS.getCode());
			jsonResult.setMsg(ResultCode.SUCCESS.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    	return jsonResult;
	}
	
}
