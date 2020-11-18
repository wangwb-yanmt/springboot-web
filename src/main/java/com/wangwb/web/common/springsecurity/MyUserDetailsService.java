//package com.wangwb.web.common.springsecurity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import com.wangwb.web.common.util.StringUtil;
//import com.wangwb.web.dao.LoginDao;
//
///**
// * 登录类，用户登录时调用的第一类
// * @author wangwb@sparknet.com.cn
// *
// */
//@Component
//public class MyUserDetailsService implements UserDetailsService{
//	
//	@Resource
//	private LoginDao loginDao;
//
//	/**
//     * 登陆验证时，通过username获取用户的所有权限信息
//     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
//     */
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		if("".equals(username)) {
//			throw new UsernameNotFoundException("用户名为空");
//		}
////		查询信息塞到MyUserDetails中
//		List<Map<String, Object>> userList = loginDao.queryUser(username);
//		MyUserDetails myUserDetail = new MyUserDetails();
//		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//		if(userList.size()==0) {
//			throw new UsernameNotFoundException("用户名不存在");
//		} else {
////			模拟这个用户的权限列表
//			GrantedAuthority grantedAuthority1 = new SimpleGrantedAuthority("add");
//			GrantedAuthority grantedAuthority2 = new SimpleGrantedAuthority("delete");
//			GrantedAuthority grantedAuthority3 = new SimpleGrantedAuthority("update");
//			GrantedAuthority grantedAuthority4 = new SimpleGrantedAuthority("query");
//			grantedAuthorities.add(grantedAuthority1);
//			grantedAuthorities.add(grantedAuthority2);
//			grantedAuthorities.add(grantedAuthority3);
//			grantedAuthorities.add(grantedAuthority4);
//			myUserDetail.setAuthorities(grantedAuthorities);
//			myUserDetail.setUsername(username);
//			myUserDetail.setPassword("111111");
//		}
//		return myUserDetail;
//    }
//	
//}
