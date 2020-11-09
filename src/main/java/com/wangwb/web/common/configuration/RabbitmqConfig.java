//package com.wangwb.web.common.configuration;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * rabbitmq配置队列和交换机
// * @author admin
// *
// */
//@Configuration
//public class RabbitmqConfig {
//	
//	// ===============以下是验证default Exchange的队列和交互机（不配置交换机）==========
//	/**
//	 * 	定义队列
//	 * @return
//	 */
//	@Bean
//    public Queue Queue1() {
//        return new Queue("队列1");
//    }
//	/**
//	 * 	定义队列
//	 * @return
//	 */
//	@Bean
//    public Queue Queue2() {
//        return new Queue("队列2");
//    }
//	
//	// ===============以下是验证Direct Exchange（直连交换机）的队列和交互机==========
//	/**
//	 * 	定义队列
//	 * @return
//	 */
//    @Bean
//    public Queue directQueueA() {
//        return new Queue("direct.A");
//    }
//    /**
//	 * 	定义队列
//	 * @return
//	 */
//    @Bean
//    public Queue directQueueB() {
//        return new Queue("direct.B");
//    }
//    /**
//	 * 	定义队列
//	 * @return
//	 */
//    @Bean
//    public Queue directQueueC() {
//        return new Queue("direct.C");
//    }
//    /**
//	 * 	定义交换机
//	 * @return
//	 */
//    @Bean
//    DirectExchange directExchange() {
//        return new DirectExchange("directExchange");
//    }
//    /**
//	 * 	队列绑定到交换机
//	 * @return
//	 */
//    @Bean
//    Binding bindingDirectExchangeA(Queue directQueueA, DirectExchange directExchange) {
//        return BindingBuilder.bind(directQueueA).to(directExchange).with("direct.a");//队列通过routingKey绑定到交换机
//    }
//    /**
//	 * 	队列绑定到交换机
//	 * @return
//	 */
//    @Bean
//    Binding bindingDirectExchangeB(Queue directQueueB, DirectExchange directExchange) {
//        return BindingBuilder.bind(directQueueB).to(directExchange).with("direct.b");
//    }
//    /**
//	 * 	队列绑定到交换机
//	 * @return
//	 */
//    @Bean
//    Binding bindingDirectExchangeC(Queue directQueueC, DirectExchange directExchange) {
//        return BindingBuilder.bind(directQueueC).to(directExchange).with("direct.c");
//    }
//	
//	// ===============以下是验证Fanout Exchange（扇形广播交换机）的队列和交互机==========
//    /**
//	 * 	定义队列
//	 * @return
//	 */
//    @Bean
//    public Queue fanoutQueueA() {
//        return new Queue("fanout.A");
//    }
//    /**
//	 * 	定义队列
//	 * @return
//	 */
//    @Bean
//    public Queue fanoutQueueB() {
//        return new Queue("fanout.B");
//    }
//    /**
//	 * 	定义队列
//	 * @return
//	 */
//    @Bean
//    public Queue fanoutQueueC() {
//        return new Queue("fanout.C");
//    }
//    /**
//	 * 	定义交换机
//	 * @return
//	 */
//    @Bean
//    FanoutExchange fanoutExchange() {
//        return new FanoutExchange("fanoutExchange");
//    }
//    /**
//	 * 	队列绑定到交换机
//	 * @return
//	 */
//    @Bean
//    Binding bindingExchangeA(Queue fanoutQueueA, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(fanoutQueueA).to(fanoutExchange);
//    }
//    /**
//	 * 	队列绑定到交换机
//	 * @return
//	 */
//    @Bean
//    Binding bindingExchangeB(Queue fanoutQueueB, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(fanoutQueueB).to(fanoutExchange);
//    }
//    /**
//	 * 	队列绑定到交换机
//	 * @return
//	 */
//    @Bean
//    Binding bindingExchangeC(Queue fanoutQueueC, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(fanoutQueueC).to(fanoutExchange);
//    }
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
