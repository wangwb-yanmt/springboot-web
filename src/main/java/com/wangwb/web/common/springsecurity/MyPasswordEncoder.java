//package com.wangwb.web.common.springsecurity;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * 自定义的密码加密方法，实现了PasswordEncoder接口
// * 这个类主要是对密码加密的处理，以及用户传递过来的密码和数据库密码（UserDetailsService中的密码）进行比对
// * @author wangwb@sparknet.com.cn
// *
// */
//@Component
//public class MyPasswordEncoder implements PasswordEncoder{
//
//	@Override
//	public String encode(CharSequence charSequence) {
//		//加密方法可以根据自己的需要修改
//		return charSequence.toString();
//    }
//
//	@Override
//	public boolean matches(CharSequence charSequence, String s) {
//		return encode(charSequence).equals(s);
//    }
//	
//}
