package com.wangwb.web.common.util;

/**
 * @author admin 验证码工具
 */
public class VerificationCodeUtil {

	/**
	 * 生成验证码
	 */
	public static String getVerificationCode() {
        String num = "";
        for (int i = 0; i < 6; i++) {
            num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
        }
        return num;
    }
	
}
