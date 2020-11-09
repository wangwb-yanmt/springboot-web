package com.wangwb.web.common.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {

	//密钥
	public static String SECRET = "springcloud";
	
	/**
	 * description:生成token
	 * @return
	 * @throws Exception
	 */
	public static String createToken(String userId) throws Exception {
		//签发时间
		Date istDate = new Date();
		
		//设置过期时间24小时
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.HOUR_OF_DAY, 24);
		Date expiresDate = nowTime.getTime();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		
		String token = JWT.create()
				.withHeader(map)
				.withClaim("userId", userId)//自定义信息
				.withExpiresAt(expiresDate)//到期时间
				.withIssuedAt(istDate)//签发时间
				.sign(Algorithm.HMAC256(SECRET));
		
		return token;
	}

	/**
	 * description:校验token
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Claim> verifyToken(String token) throws Exception{
		//通过密钥解析
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
		DecodedJWT jwt = null;
		try {
			jwt = verifier.verify(token);
		} catch (Exception e) {
			throw new RuntimeException("凭证失效！");
		}
		
		return jwt.getClaims();
	}
	
	
	

}