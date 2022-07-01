package com.Prokeep.Prokeep.assessment1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


//Enables Asnyc
@Configuration
@EnableAsync
@Profile("!non-async")
public class SpringAsyncConfig {

    /*
    Threads outside of pool are put into a queue and therefore messages will be processed in order. If the pool
    is 2 or more than threads in the pool are selected at random order.
     */
    @Bean
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setThreadNamePrefix("message-processor-");
        executor.initialize();
        return executor;
    }
}
