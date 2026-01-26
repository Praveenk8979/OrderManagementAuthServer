package com.ordermanagement.OrderManagementAndAuthServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "emailExecutor")
    public Executor emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);      // min threads
        executor.setMaxPoolSize(10);      // max threads
        executor.setQueueCapacity(100);   // pending emails
        executor.setThreadNamePrefix("EMAIL-");
        executor.initialize();
        return executor;
    }
}
