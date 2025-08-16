package com.intern.design.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    public final static String EVENT_THREAD_POOL = "EVENT_THREAD_POOL";

    @Bean(EVENT_THREAD_POOL)
    public ThreadPoolExecutor eventThreadPool() {
        return new ThreadPoolExecutor(
                3,
                5,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1024)
        );
    }
}
