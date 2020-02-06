package com.ms.server.thread;

import com.ms.server.comm.RedisChannelListener;
import com.ms.server.comm.SysConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by wangjian on 2017/8/29.
 */
@Configuration
public class TaskExecutePool {

    private static final Logger log = LoggerFactory.getLogger(TaskExecutePool.class);

    @Autowired
    private SysConfig config;

    @Bean
    public Executor TaskExecutor() {
        log.info("Start TaskExecute.....");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        executor.setThreadNamePrefix("UDPExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：用于被拒绝任务的处理程序，它直接在execute方法的调用线程中运行被拒绝的任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}