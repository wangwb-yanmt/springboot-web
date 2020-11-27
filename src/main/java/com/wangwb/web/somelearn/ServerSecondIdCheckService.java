//package test;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//
//import com.alibaba.fastjson.JSON;
//
//import cn.com.sparknet.AuthAPI.external.v1.interfaces.AppLog;
//import cn.com.sparknet.AuthAPI.external.v1.server.dao.ServerGetTicketDao;
//import cn.com.sparknet.AuthAPI.external.v1.server.dao.ServerSecondIdCheckDao;
//import cn.com.sparknet.v1.util.StringUtil;
//
///**
// * 外部接口-二次身份校验
// * 
// * @author wangwb
// *
// */
//@Service
//@Transactional
//public class ServerSecondIdCheckService {
//
//	@Autowired
//	private RestTemplate restTemplate;
//
//	@Autowired
//	private AmqpTemplate amqpTemplate;
//
//	@Autowired
//	private StringRedisTemplate stringRedisTemplate;
//
//	@Autowired
//	private ServerGetTicketDao serverGetTicketDao;// 验证appid和appkey用
//
//	@Autowired
//	private ServerSecondIdCheckDao serverSecondIdCheckDao;// 本
//
//	/**
//	 * 账号密码方式验证提交
//	 * 
//	 * @param paramMap
//	 * @return
//	 */
//	@AppLog
//	public Map<String, Object> accountCheck(Map<String, Object> paramMap) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		// 子系统传来的参数
//		String tokenid = StringUtil.nullToEmpty(paramMap.get("tokenid"));// 过滤器验证
//		String ticketid = StringUtil.nullToEmpty(paramMap.get("ticketid"));// 过滤器验证
//		String appid = StringUtil.nullToEmpty(paramMap.get("appid"));
//		String appkey = StringUtil.nullToEmpty(paramMap.get("appkey"));
//		String account = StringUtil.nullToEmpty(paramMap.get("account"));// 是否需要解密，另说
//		String password = StringUtil.nullToEmpty(paramMap.get("password"));// 是否需要解密，另说
//		String secondCheckId = StringUtil.nullToEmpty(paramMap.get("secondCheckId"));// 携带的临时验证票据
//		// 非空校验
//		if (StringUtil.isEmpty(appid) || StringUtil.isEmpty(appkey)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "账号密码验证失败：请检查appid和appkey是否为空");
//			return resultMap;
//		}
//		try {
//			// 验证appId和appKey
//			Map<String, Object> appInfoMap = serverGetTicketDao.isAppIdAndKeyExisted(paramMap);
//			if (appInfoMap == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "账号密码验证失败：appid和appkey不匹配");
//				return resultMap;
//			}
//			// 查询账号和密码是否匹配
//			Map<String, Object> userMap = serverSecondIdCheckDao.queryUserMap(account, password);
//			if (userMap == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "账号密码验证失败：请检查账号密码是否正确");
//				return resultMap;
//			} else {
//				String USER_ID = StringUtil.nullToEmpty(userMap.get("USER_ID"));
//				// 根据secondCheckId得到userId
//				String userid = stringRedisTemplate.opsForValue().get("secondCheckId:" + secondCheckId);
//				if (USER_ID.equals(userid)) {
//					resultMap.put("success", true);
//					resultMap.put("msg", "账号密码验证成功");
//				} else {
//					resultMap.put("success", false);
//					resultMap.put("msg", "账号密码验证失败：用户不匹配");
//				}
//			}
//		} catch (Exception e) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "账号密码验证失败：发生异常");
//			throw new RuntimeException(e);
//		}
//		return resultMap;
//	}
//
//	/**
//	 * 短信方式验证提交
//	 * 
//	 * @param paramMap
//	 * @return
//	 */
//	@AppLog
//	public Map<String, Object> codeCheck(Map<String, Object> paramMap) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		// 子系统传来的参数
//		String tokenid = StringUtil.nullToEmpty(paramMap.get("tokenid"));// 过滤器验证
//		String ticketid = StringUtil.nullToEmpty(paramMap.get("ticketid"));// 过滤器验证
//		String appid = StringUtil.nullToEmpty(paramMap.get("appid"));
//		String appkey = StringUtil.nullToEmpty(paramMap.get("appkey"));
//		String phoneNumber = StringUtil.nullToEmpty(paramMap.get("phoneNumber"));// 手机号码
//		String code = StringUtil.nullToEmpty(paramMap.get("code"));// 验证码
//		String secondCheckId = StringUtil.nullToEmpty(paramMap.get("secondCheckId"));// 携带的临时验证票据
//		// 非空校验
//		if (StringUtil.isEmpty(appid) || StringUtil.isEmpty(appkey)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "验证失败：请检查appid和appkey是否为空");
//			return resultMap;
//		}
//		if (StringUtil.isEmpty(phoneNumber) || StringUtil.isEmpty(code)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "验证失败：请检查手机号和验证码是否为空");
//			return resultMap;
//		}
//		try {
//			// 验证appId和appKey
//			Map<String, Object> appInfoMap = serverGetTicketDao.isAppIdAndKeyExisted(paramMap);
//			if (appInfoMap == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "验证失败：appid和appkey不匹配");
//				return resultMap;
//			}
//			// 校验手机号的有效性
//			Map<String, Object> checkUserPhoneNumber = serverSecondIdCheckDao.checkUserPhoneNumber(phoneNumber);
//			if (checkUserPhoneNumber == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "验证失败：该手机号未绑定任务用户");
//				return resultMap;
//			}
//			// 手机号对应的userId
//			String userId1 = StringUtil.nullToEmpty(checkUserPhoneNumber.get("USER_ID"));
//			// 根据secondCheckId得到userId
//			String userId2 = stringRedisTemplate.opsForValue().get("secondCheckId:" + secondCheckId);
//			if (!userId1.equals(userId2)) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "验证失败：该手机号绑定的用户与验证目标用户不匹配");
//				return resultMap;
//			}
//			// -------------分隔线--------------------------
//			Map<String, Object> msgInfoMap = serverSecondIdCheckDao.queryMsgInfo(phoneNumber, code);
//			if (msgInfoMap != null) {
//				String smsId = StringUtil.nullToEmpty(msgInfoMap.get("SMS_ID"));
//				// 更新数据状态
//				serverSecondIdCheckDao.updateMsg(smsId);
//				resultMap.put("success", true);
//				resultMap.put("msg", "验证成功");
//			} else {
//				resultMap.put("success", false);
//				resultMap.put("msg", "验证失败：验证码错误或已过期");
//			}
//		} catch (Exception e) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "验证失败：发生异常");
//			throw new RuntimeException(e);
//		}
//		return resultMap;
//	}
//
//	/**
//	 * 获取本次验证页面地址
//	 * 
//	 * @param paramMap
//	 * @return
//	 */
//	@AppLog
//	public Map<String, Object> getAuthUrl(Map<String, Object> paramMap) {
//		Properties properties = new Properties();
//		InputStream is = this.getClass().getResourceAsStream("init.properties");
//		try {
//			properties.load(is);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		String url = properties.getProperty("authUrl");
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		// 子系统传来的参数
//		String tokenid = StringUtil.nullToEmpty(paramMap.get("tokenid"));// 过滤器验证
//		String ticketid = StringUtil.nullToEmpty(paramMap.get("ticketid"));// 过滤器验证
//		String appid = StringUtil.nullToEmpty(paramMap.get("appid"));
//		String appkey = StringUtil.nullToEmpty(paramMap.get("appkey"));
//		// 非空校验
//		if (StringUtil.isEmpty(appid) || StringUtil.isEmpty(appkey)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "获取页面地址失败：请检查appid和appkey是否为空");
//			return resultMap;
//		}
//		try {
//			// 验证appId和appKey
//			Map<String, Object> appInfoMap = serverGetTicketDao.isAppIdAndKeyExisted(paramMap);
//			if (appInfoMap == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "获取页面地址失败：appid和appkey不匹配");
//				return resultMap;
//			}
//			// 查询页面地址（库or配置文件？）
//			resultMap.put("success", true);
//			resultMap.put("msg", "获取页面地址成功");
//			resultMap.put("data", url);
//		} catch (Exception e) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "获取页面地址失败：发生异常");
//			throw new RuntimeException(e);
//		}
//		return resultMap;
//	}
//
//	/**
//	 * 短信方式验证-发送验证码
//	 * 
//	 * @param paramMap
//	 * @return
//	 */
//	@AppLog
//	public Map<String, Object> getCodeForMsgCheck(Map<String, Object> paramMap) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		// 子系统传来的参数
//		String tokenid = StringUtil.nullToEmpty(paramMap.get("tokenid"));// 过滤器验证
//		String ticketid = StringUtil.nullToEmpty(paramMap.get("ticketid"));// 过滤器验证
//		String appid = StringUtil.nullToEmpty(paramMap.get("appid"));
//		String appkey = StringUtil.nullToEmpty(paramMap.get("appkey"));
//		String phoneNumber = StringUtil.nullToEmpty(paramMap.get("phoneNumber"));// 用户填入的手机号码
//		String secondCheckId = StringUtil.nullToEmpty(paramMap.get("secondCheckId"));// 携带的临时验证票据
//		// 非空校验
//		if (StringUtil.isEmpty(appid) || StringUtil.isEmpty(appkey)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "发送验证码失败：请检查appid和appkey是否为空");
//			return resultMap;
//		}
//		try {
//			// 验证appId和appKey
//			Map<String, Object> appInfoMap = serverGetTicketDao.isAppIdAndKeyExisted(paramMap);
//			if (appInfoMap == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "发送验证码失败：appid和appkey不匹配");
//				return resultMap;
//			}
//			// 校验手机号的有效性
//			String userid = stringRedisTemplate.opsForValue().get("tokenid:" + tokenid);
//			Map<String, Object> checkUserPhoneNumber = serverSecondIdCheckDao.checkUserPhoneNumber(phoneNumber);
//			if (checkUserPhoneNumber == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "发送验证码失败：该手机号未绑定任务用户");
//				return resultMap;
//			}
//			// 查询出手机号对应的userId
//			String userId1 = StringUtil.nullToEmpty(checkUserPhoneNumber.get("USER_ID"));
//			// 根据secondCheckId得到userId
//			String userId2 = stringRedisTemplate.opsForValue().get("secondCheckId:" + secondCheckId);
//			if (!userId1.equals(userId2)) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "发送验证码失败：该手机号绑定的用户与验证目标用户不匹配");
//				return resultMap;
//			}
//			// 以上校验成功后，先数据入库，再加入mq消息队列，发送验证码 （交换机：CalonDirectExchange
//			// key：CalonDirectRouting）
//			int random = (int) ((Math.random() * 9 + 1) * 100000);
//			String code = random + "";
//			String seqnextval = serverSecondIdCheckDao.queryNextValue();
//			// 入库
//			serverSecondIdCheckDao.insertMsgRecord(userid, seqnextval, phoneNumber, code);
//			// 入队列
//			Map<String, Object> tempMap = new HashMap<String, Object>();
//			tempMap.put("code", code);
//			tempMap.put("MOBILE_PHONE", phoneNumber);
//			tempMap.put("USERID", userid);
//			tempMap.put("seqnextval", seqnextval);
//			amqpTemplate.convertAndSend("q_fanout_A", tempMap);
//			resultMap.put("success", true);
//			resultMap.put("msg", "发送验证码成功");
//		} catch (Exception e) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "发送验证码失败：发生异常");
//			throw new RuntimeException(e);
//		}
//		return resultMap;
//	}
//
//	/**
//	 * 获取本次验证需要的临时凭据
//	 * 
//	 * @param paramMap
//	 * @return
//	 */
//	@AppLog
//	public Map<String, Object> getTempuuid(Map<String, Object> paramMap) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		// 子系统传来的参数
//		String tokenid = StringUtil.nullToEmpty(paramMap.get("tokenid"));// 过滤器验证
//		String ticketid = StringUtil.nullToEmpty(paramMap.get("ticketid"));// 过滤器验证
//		String appid = StringUtil.nullToEmpty(paramMap.get("appid"));
//		String appkey = StringUtil.nullToEmpty(paramMap.get("appkey"));
//		String targetUserId = StringUtil.nullToEmpty(paramMap.get("targetUserId"));
//		// 非空校验
//		if (StringUtil.isEmpty(appid) || StringUtil.isEmpty(appkey)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "获取临时票据失败：请检查appid和appkey是否为空");
//			return resultMap;
//		}
//		if (StringUtil.isEmpty(targetUserId)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "获取临时票据失败：请检查targetUserId是否为空");
//			return resultMap;
//		}
//		try {
//			// 验证appId和appKey
//			Map<String, Object> appInfoMap = serverGetTicketDao.isAppIdAndKeyExisted(paramMap);
//			if (appInfoMap == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "获取临时票据失败：appid和appkey不匹配");
//				return resultMap;
//			}
//			// 存入redis
//			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//			stringRedisTemplate.opsForValue().set("secondCheckId:" + uuid, targetUserId, Long.parseLong("300000"), TimeUnit.MILLISECONDS);
//			resultMap.put("success", true);
//			resultMap.put("msg", "获取临时票据成功");
//			resultMap.put("data", uuid);
//		} catch (Exception e) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "获取临时票据失败：发生异常");
//			throw new RuntimeException(e);
//		}
//		return resultMap;
//	}
//
//	/**
//	 * 查询登陆方式字典
//	 * 
//	 * @param paramMap
//	 * @return
//	 */
//	@AppLog
//	public Map<String, Object> queryLoginType(Map<String, Object> paramMap) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		// 子系统传来的参数
//		String tokenid = StringUtil.nullToEmpty(paramMap.get("tokenid"));// 过滤器验证
//		String ticketid = StringUtil.nullToEmpty(paramMap.get("ticketid"));// 过滤器验证
//		String appid = StringUtil.nullToEmpty(paramMap.get("appid"));
//		String appkey = StringUtil.nullToEmpty(paramMap.get("appkey"));
//		// 非空校验
//		if (StringUtil.isEmpty(appid) || StringUtil.isEmpty(appkey)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "查询登陆方式失败：请检查appid和appkey是否为空");
//			return resultMap;
//		}
//		try {
//			// 验证appId和appKey
//			Map<String, Object> appInfoMap = serverGetTicketDao.isAppIdAndKeyExisted(paramMap);
//			if (appInfoMap == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "查询登陆方式失败：appid和appkey不匹配");
//				return resultMap;
//			}
//			List<Map<String, Object>> dataList = serverSecondIdCheckDao.queryLoginType();
//			resultMap.put("success", true);
//			resultMap.put("msg", "查询登陆方式成功");
//			resultMap.put("data", dataList);
//		} catch (Exception e) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "查询登陆方式失败：发生异常");
//			throw new RuntimeException(e);
//		}
//		return resultMap;
//	}
//
//	/**
//	 * 微信方式验证-展示二维码
//	 * 
//	 * @param paramMap
//	 * @return
//	 */
//	@AppLog
//	public Map<String, Object> showQRcode(Map<String, Object> paramMap) {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		// 子系统传来的参数
//		String tokenid = StringUtil.nullToEmpty(paramMap.get("tokenid"));// 过滤器验证
//		String ticketid = StringUtil.nullToEmpty(paramMap.get("ticketid"));// 过滤器验证
//		String appid = StringUtil.nullToEmpty(paramMap.get("appid"));
//		String appkey = StringUtil.nullToEmpty(paramMap.get("appkey"));
//		String secondCheckId = StringUtil.nullToEmpty(paramMap.get("secondCheckId"));// 携带的临时验证票据
//		// 非空校验
//		if (StringUtil.isEmpty(appid) || StringUtil.isEmpty(appkey)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "请求失败：请检查appid和appkey是否为空");
//			return resultMap;
//		}
//		try {
//			// 验证appId和appKey
//			Map<String, Object> appInfoMap = serverGetTicketDao.isAppIdAndKeyExisted(paramMap);
//			if (appInfoMap == null) {
//				resultMap.put("success", false);
//				resultMap.put("msg", "请求失败：appid和appkey不匹配");
//				return resultMap;
//			}
//			String userid = stringRedisTemplate.opsForValue().get("tokenid:" + tokenid);
//			// -------------分隔线--------------------------
//			// 应用注册的appId
//			String weChatAppId = "wx08c1ad54e6140b6a";
//			// 回调addr
//			String redirectUri = "http://api.gubin.work/dev/weixin/?callurl=http://10.66.1.56:9101/AuthAPI/external/v1/server/weChatCallBack";
//			// 查询应用注册的appId
//			Map<String, Object> weChatAppIdMap = serverSecondIdCheckDao.queryParameter("wechatIdentifier");
//			if (weChatAppIdMap != null) {
//				weChatAppId = StringUtil.nullToEmpty(weChatAppIdMap.get("PARAMETER_VALUE"));
//			}
//			// 查询二次验证微信回调addr（这需要在库里面配置吗？）
//			Map<String, Object> redirectUriMap = serverSecondIdCheckDao.queryParameter("wechatAuthSdkAddr");
//			if (redirectUriMap != null) {
//				redirectUri = StringUtil.nullToEmpty(redirectUriMap.get("PARAMETER_VALUE"));// "http://api.gubin.work/dev/weixin/?callurl=http://10.66.1.74:9101/AuthAPI/external/v1/server/weChatCallBack&secondCheckId="
//			}
//			redirectUri = redirectUri + "?secondCheckId=" + secondCheckId;
//			String QRcodeServiceUrl = "https://open.weixin.qq.com/connect/qrconnect?appid=" + weChatAppId + "&redirect_uri=" + redirectUri + "&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
//			resultMap.put("success", true);
//			resultMap.put("msg", "请求成功");
//			resultMap.put("data", QRcodeServiceUrl);
//		} catch (Exception e) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "请求失败：发生异常");
//			throw new RuntimeException(e);
//		}
//		return resultMap;
//	}
//
//	/**
//	 * 微信方式验证-授权确认后回调页面请求方法
//	 * 
//	 * @param paramMap
//	 * @return
//	 * @throws IOException
//	 */
//	public void weChatCallBack(Map<String, Object> paramMap, HttpServletResponse response) throws IOException {
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		// 得到微信给的code
//		String code = StringUtil.nullToEmpty(paramMap.get("code"));
//		String secondCheckId = StringUtil.nullToEmpty(paramMap.get("secondCheckId"));
//		// 库中查询统一认证的微信注册信息
//		String appid = "wx08c1ad54e6140b6a";
//		String secret = "575d43bd07b1ec5dd60be5be2cba6f34";
//		Map<String, Object> appidInfo = serverSecondIdCheckDao.queryParameter("wechatIdentifier");
//		if (appidInfo != null) {
//			appid = StringUtil.nullToEmpty(appidInfo.get("PARAMETER_VALUE"));
//		}
//		Map<String, Object> serectInfo = serverSecondIdCheckDao.queryParameter("wechatSecret");
//		if (serectInfo != null) {
//			secret = StringUtil.nullToEmpty(serectInfo.get("PARAMETER_VALUE"));
//		}
//		// 查询微信令牌信息
//		String weChatInfo = restTemplate.getForObject("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&grant_type=authorization_code&code=" + code, String.class);
//		Map<String, Object> weChatInfoMap = JSON.parseObject(weChatInfo);
//		String openId = StringUtil.nullToEmpty(weChatInfoMap.get("openid"));// 授权用户唯一标识
//		String access_token = StringUtil.nullToEmpty(weChatInfoMap.get("access_token "));// 接口调用凭证
//		String refresh_token = StringUtil.nullToEmpty(weChatInfoMap.get("refresh_token "));// 用户刷新access_token的凭证
//		// 通过令牌查询微信用户信息
////		String weChatUserInfo = restTemplate.getForObject("api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openId,String.class);
////		Map<String,Object> weChatUserInfoMap = JSON.parseObject(weChatUserInfo);
//
//		Map<String, Object> userMap = serverSecondIdCheckDao.queryUser(openId);
//		if (userMap == null) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "验证失败：该微信未绑定用户");
//			response.sendRedirect("http://10.66.1.56:9003/Auth-SDK/page/tip.html?success=false&msg=" + URLEncoder.encode("验证失败：该微信未绑定用户", "UTF-8"));
//		}
//		String userId1 = StringUtil.nullToEmpty(userMap.get("USER_ID"));
//		// 根据secondCheckId得到userId
//		String userId2 = stringRedisTemplate.opsForValue().get("secondCheckId:" + secondCheckId);
//		if (!userId1.equals(userId2)) {
//			resultMap.put("success", false);
//			resultMap.put("msg", "验证失败：该微信号绑定的用户与验证目标用户不匹配");
//			response.sendRedirect("http://10.66.1.56:9003/Auth-SDK/page/tip.html?success=false&msg=" + URLEncoder.encode("验证失败：该微信号绑定的用户与验证目标用户不匹配", "UTF-8"));
//		}
//		resultMap.put("success", true);
//		resultMap.put("msg", "验证成功");
//		resultMap.put("targetPage", "http://www.baidu.com");
//		response.sendRedirect("http://10.66.1.56:9003/Auth-SDK/page/tip.html?success=false&msg=" + URLEncoder.encode("验证成功", "UTF-8") + "&targetPage=http://www.baidu.com");
////		PrintWriter pw = response.getWriter();
////		pw.write(resultMap.toString());
//	}
//
//}
