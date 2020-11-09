package com.wangwb.web.common.configuration;

import java.util.concurrent.Executors;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * spring task定时任务配置类，多线程执行
 */

@Configuration
public class TimerTaskConfig implements SchedulingConfigurer {

	@Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        
        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
    }
}
