package com.cyx.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadConfig {
    @Bean("logThreadPoolExecutor")
    public ThreadPoolExecutor newThreadPoolExecutor(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
