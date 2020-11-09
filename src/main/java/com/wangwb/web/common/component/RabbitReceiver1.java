//package com.wangwb.web.common.component;
//
//import java.io.IOException;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
///**
// * description:消息接收者
// * @author wangwb
// *
// */
//@Component
//public class RabbitReceiver1 {
//
//	//指定接收哪个队列的消息
//	@RabbitListener(queues = "队列1")
//    public void process1(String message) throws IOException {
//		//业务逻辑
//        System.out.println("接收队列【队列1】的消息："+message);
//    }
//	
//	@RabbitListener(queues = "direct.A")
//    public void process2(String message) throws IOException {
//        System.out.println("接收队列【direct.A】的消息："+message);
//    }
//	@RabbitListener(queues = "direct.B")
//    public void process3(String message) throws IOException {
//        System.out.println("接收队列【direct.B】的消息："+message);
//    }
//	
//	@RabbitListener(queues = "fanout.A")
//    public void process4(String message) throws IOException {
//        System.out.println("接收队列【fanout.A】的消息："+message);
//    }
//	@RabbitListener(queues = "fanout.B")
//    public void process5(String message) throws IOException {
//        System.out.println("接收队列【fanout.B】的消息："+message);
//    }
//	@RabbitListener(queues = "fanout.C")
//    public void process6(String message) throws IOException {
//        System.out.println("接收队列【fanout.C】的消息："+message);
//    }
//	
//}
