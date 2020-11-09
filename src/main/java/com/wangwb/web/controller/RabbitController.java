package com.wangwb.web.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/RabbitController.do")
public class RabbitController {
	
	@Autowired
	private AmqpTemplate myRabbitTemplate;
	
	@RequestMapping(params="oneToOne")
	public void oneToOne() {
		myRabbitTemplate.convertAndSend("队列1","消息object");
	}
	
	@RequestMapping(params="directSend")
	public void directSend() {
    	myRabbitTemplate.convertAndSend("directExchange","direct.a", "消息object");
    }
	
	@RequestMapping(params="fanoutSend")
	public void fanoutSend() {
    	myRabbitTemplate.convertAndSend("fanoutExchange","", "消息object");
    }
	
}
