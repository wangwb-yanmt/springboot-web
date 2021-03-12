package com.wangwb.web.items.springsecurity;
//package com.wangwb.web.common.springsecurity;
//
//
//import java.io.PrintWriter;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.authentication.AccountExpiredException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.CredentialsExpiredException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.InternalAuthenticationServiceException;
//import org.springframework.security.authentication.LockedException;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.wangwb.web.common.bean.JsonResult;
//import com.wangwb.web.common.myenum.ResultCode;
//
//import net.sf.json.JSONObject;
//
//
///**
// * spring security配置类
// * @author wangwb@sparknet.com.cn
// *
// */
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
//
//    
//    @Bean
//    public UserDetailsService userDetailsService() {
//        //获取用户账号密码及权限信息
//        return new MyUserDetailsService();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        // 设置默认的加密方式（强hash方式加密）
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }
//    
//    //自定义密码加密
////    @Autowired
////    private MyPasswordEncoder myPasswordEncoder;
//    
////    @Autowired
////    private UserDetailsService myCustomUserService;
////    
////    @Bean
////    public AuthenticationProvider authenticationProvider() {
////        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
////        //对默认的UserDetailsService进行覆盖
////        authenticationProvider.setUserDetailsService(myCustomUserService);
////        authenticationProvider.setPasswordEncoder(myPasswordEncoder);
////        return authenticationProvider;
////    }
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
////		 http.authenticationProvider(authenticationProvider())
////	        .httpBasic()
////	        //未登录时，进行json格式的提示，很喜欢这种写法，不用单独写一个又一个的类
////	            .authenticationEntryPoint((request,response,authException) -> {
////	                response.setContentType("application/json;charset=utf-8");
////	                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
////	                PrintWriter out = response.getWriter();
////	                Map<String,Object> map = new HashMap<String,Object>();
////	                map.put("code",403);
////	                map.put("message","未登录");
////	                out.write(objectMapper.writeValueAsString(map));
////	                out.flush();
////	                out.close();
////	            });
//		
//		http.cors().and().csrf().disable();
//		http.authorizeRequests()
//			.antMatchers("/getUser").hasAuthority("query_user")
//			.antMatchers("/public/login.html").permitAll();
////                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
////                    @Override
////                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
////                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
////                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
////                        return o;
////                    }
////                }).
//		//登入
//		http.formLogin()
//			.permitAll()//允许所有用户
//			.loginPage("/public/login.html")
//			.loginProcessingUrl("/login")
//			.usernameParameter("userName")
//			.passwordParameter("password")
//			.successHandler((request,response,authentication) -> {
//				HttpSession session=request.getSession();
//				JsonResult jsonResult = new JsonResult(true,0,"登录成功", authentication);
//				JSONObject jsonObject = JSONObject.fromObject(jsonResult);
//				response.setContentType("application/json;charset=utf-8");
//				PrintWriter out = response.getWriter();
//				out.write(jsonObject.toString());
//				out.flush();
//				out.close();
//			})
//			.failureHandler((request,response,ex) -> {
//				response.setContentType("application/json;charset=utf-8");
//				JsonResult jsonResult = null;
//				PrintWriter out = response.getWriter();
//				if (ex instanceof AccountExpiredException) {
//					//账号过期
//					jsonResult = new JsonResult(false,ResultCode.USER_ACCOUNT_EXPIRED.getCode(),ResultCode.USER_ACCOUNT_EXPIRED.getMessage());
//				} else if (ex instanceof BadCredentialsException) {
//		            //用户名密码错误
//					jsonResult = new JsonResult(false,ResultCode.USER_ACCOUNT_ERROR.getCode(),ResultCode.USER_ACCOUNT_ERROR.getMessage());
//				} else if (ex instanceof CredentialsExpiredException) {
//		            //密码过期
//					jsonResult = new JsonResult(false,ResultCode.USER_PASSWORD_EXPIRED.getCode(),ResultCode.USER_PASSWORD_EXPIRED.getMessage());
//				} else if (ex instanceof DisabledException) {
//		            //账号不可用
//					jsonResult = new JsonResult(false,ResultCode.USER_ACCOUNT_DISABLE.getCode(),ResultCode.USER_ACCOUNT_DISABLE.getMessage());
//				} else if (ex instanceof LockedException) {
//		            //账号锁定
//					jsonResult = new JsonResult(false,ResultCode.USER_ACCOUNT_LOCKED.getCode(),ResultCode.USER_ACCOUNT_LOCKED.getMessage());
//				} else if (ex instanceof InternalAuthenticationServiceException) {
//		            //账号不存在
//					jsonResult = new JsonResult(false,ResultCode.USER_ACCOUNT_NOT_EXIST.getCode(),ResultCode.USER_ACCOUNT_NOT_EXIST.getMessage());
//				} else{
//		            //其他错误
//					jsonResult = new JsonResult(false,ResultCode.FAIL.getCode(),ResultCode.FAIL.getMessage());
//		        }
//				JSONObject jsonObject = JSONObject.fromObject(jsonResult);
//				out.write(jsonObject.toString());
//				out.flush();
//				out.close();
//            });//登录失败处理逻辑
//		//登出
//		http.logout()
//			.logoutUrl("/LoginOutController/loginOut")
//			.logoutSuccessUrl("/public/login.html")
//			.logoutSuccessHandler((request,response,authentication) -> {
//				JsonResult jsonResult = new JsonResult(true,0,"退出成功", authentication);
//				JSONObject jsonObject = JSONObject.fromObject(jsonResult);
//				response.setContentType("application/json;charset=utf-8");
//				PrintWriter out = response.getWriter();
//				out.write(jsonObject.toString());
//				out.flush();
//				out.close();
//			})
//			.deleteCookies("JSESSIONID");
//		//异常处理(权限拒绝、登录失效等)
//		http.exceptionHandling()
//			.accessDeniedHandler((request,response,ex) -> {
//                response.setContentType("application/json;charset=utf-8");
//                JsonResult jsonResult = new JsonResult(false,ResultCode.NO_PERMISSION.getCode(),ResultCode.NO_PERMISSION.getMessage());
//                JSONObject jsonObject = JSONObject.fromObject(jsonResult);
//                PrintWriter out = response.getWriter();
//                out.write(jsonObject.toString());
//                out.flush();
//                out.close();
//            })
//			.authenticationEntryPoint((request,response,authException) -> {
//                response.setContentType("application/json;charset=utf-8");
//                JsonResult jsonResult = new JsonResult(false,ResultCode.USER_NOT_LOGIN.getCode(),ResultCode.USER_NOT_LOGIN.getMessage());
//                JSONObject jsonObject = JSONObject.fromObject(jsonResult);
//                PrintWriter out = response.getWriter();
//                out.write(jsonObject.toString());
//                out.flush();
//                out.close();
//            });
//		//会话管理
//		http.sessionManagement()
//			.maximumSessions(1)//同一账号同时登录最大用户数
//			.expiredSessionStrategy((sessionInformationExpiredEvent) -> {
//				HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
//				response.setContentType("application/json;charset=utf-8");
//				JsonResult jsonResult = new JsonResult(false,ResultCode.USER_ACCOUNT_USE_BY_OTHERS.getCode(),ResultCode.USER_ACCOUNT_USE_BY_OTHERS.getMessage());
//				JSONObject jsonObject = JSONObject.fromObject(jsonResult);
//				PrintWriter out = response.getWriter();
//				out.write(jsonObject.toString());
//				out.flush();
//				out.close();
//            });
//	}
//	
//	
//}
