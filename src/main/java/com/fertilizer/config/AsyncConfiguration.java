package com.fertilizer.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {
	
	@Bean(name="asyncExecutor")
	public Executor asyncExecuter() {
		
		ThreadPoolTaskExecutor executer = new ThreadPoolTaskExecutor();
		executer.setCorePoolSize(50);
		executer.setMaxPoolSize(100);
		executer.setQueueCapacity(1000);
		executer.setThreadNamePrefix("AsyncThread:-");
		executer.initialize();
		return executer;
		
	}

}
