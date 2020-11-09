package com.wangwb.web.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


/**
 * 	邮件发送
 * @author wangwb
 *
 */
@Component
public class MailUtil {

	@Autowired
	private static JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private static String from;
	
	public static void sendMail(String to,String subject,String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		mailSender.send(message);
	}
	
}
