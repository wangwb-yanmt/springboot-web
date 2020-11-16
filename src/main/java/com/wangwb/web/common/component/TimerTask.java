package com.wangwb.web.common.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*cron：cron表达式，指定任务在特定时间执行；
fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms；
fixedDelayString：与fixedDelay含义一样，只是参数类型变为String；
fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms；
fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String；
initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms；
initialDelayString：与initialDelay的含义一样，只是将参数类型变为String；
zone：时区，默认为当前时区，一般没有用到。*/

/**
 * 	定时任务类
 * @author wangwb
 *
 */
@Component
public class TimerTask {

	private Logger logger = LoggerFactory.getLogger(TimerTask.class);

	@Scheduled(cron="0/30 * * * * *")
	public void task1() {
		logger.info("30秒一次");
	}
	
	/*@Scheduled(fixedRate = 5000)
	public void task2() {
		System.out.println("5秒钟执行一次");
	}
	
	@Scheduled(fixedDelay = 5000)
	public void task3() {
		System.out.println("5秒钟执行一次");
	}*/
}