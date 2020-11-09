package com.wangwb.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wangwb.web.common.bean.UserBean;
import com.wangwb.web.common.exception.MyException;
import com.wangwb.web.common.util.ParamUtil;
import com.wangwb.web.common.util.StringUtil;
import com.wangwb.web.service.TestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;



/**
 * 	测试controller
 * @author wangwb
 *
 */
@Api(tags = "测试模块")
@RestController
@RequestMapping("/TestController")
public class TestController {
	
	@Resource
	private TestService testService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 	返回列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "返回列表")
	@PostMapping("/getList")
	public Map<String, Object> getList(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
//		UserBean userBean = (UserBean) request.getSession().getAttribute("sessionBean");
//      String loginUserId = StringUtil.nullToEmpty(userBean.getUserId());
		return testService.getList(paramsMap);
		
	} 
	
	/**
	 * 	查询树测试
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "查询树测试")
	@PostMapping("/queryTree")
	public Map<String, Object> queryTree(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		UserBean userBean = (UserBean) request.getSession().getAttribute("sessionBean");
        String loginUserId = userBean.getUserId();
		return testService.queryTree(paramsMap,loginUserId);
		
	} 
	
	
	/**
	 * 	测试更新数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/updateData")
	public Map<String, Object> updateData(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		UserBean userBean = (UserBean) request.getSession().getAttribute("sessionBean");
        String loginUserId = userBean.getUserId();
		return testService.updateData(paramsMap,loginUserId);
		
	} 
	
	@ApiOperation(value = "查询左侧菜单")
	@PostMapping("/queryModule")
	public Map<String, Object> queryModule(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		UserBean userBean = (UserBean) request.getSession().getAttribute("sessionBean");
        String loginUserId = userBean.getUserId();
		return testService.queryModule(paramsMap,loginUserId);
		
	} 
	
	@ApiOperation(value = "查询登录用户信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户的唯一标识", required = true),
        @ApiImplicitParam(name = "name", value = "用户名称", required = true)
	})
	@PostMapping("/queryLoginInfo")
	public Map<String, Object> queryLoginInfo(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		UserBean userBean = (UserBean) request.getSession().getAttribute("sessionBean");
        String loginId = userBean.getUserId();
		return testService.queryLoginInfo(paramsMap,loginId);
	} 
	
	@ApiOperation(value = "获取微信用户唯一标识")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "code", value = "本次登录的code", required = true)
	})
	@RequestMapping("/getWxcode")
	public Map<String, Object> getWxcode(HttpServletRequest request,@RequestParam("code") String code) throws Exception {
		String appId = "wx3e8ec7c89e214279";
		String appSecret = "06c930e9f3354eac724d561cadadb1d4";
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+
				"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
		String result = restTemplate.getForObject(url, String.class);
		JSONObject jsonObject = JSONObject.fromObject(result);
		Map<String, Object> resultMap = (Map<String, Object>) jsonObject;
		return resultMap;
	} 
	
	@ApiOperation(value = "校验微信唯一标识")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "openid", value = "微信唯一标识", required = true)
	})
	@RequestMapping("/checkWxuser")
	public Map<String, Object> checkWxuser(HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		return resultMap;
	} 
	
	@ApiOperation(value = "获取微信小程序表单数据测试")
	@RequestMapping("/lookFormData")
	public Map<String, Object> lookFormData(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		for(Entry<String, Object> entry : paramsMap.entrySet()) {
			String key = entry.getKey();
			String value = StringUtil.nullToEmpty(entry.getValue());
			System.out.println(key+":"+value);
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		return resultMap;
	} 
	

}
