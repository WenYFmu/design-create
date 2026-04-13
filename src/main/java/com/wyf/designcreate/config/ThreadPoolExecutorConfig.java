package com.wyf.designcreate.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池执行器配置类
 * 为系统中不同的业务场景配置独立的线程池，提高并发处理能力
 */
@Configuration
public class ThreadPoolExecutorConfig {

    /**
     * CPU核心数，用于动态计算线程池大小
     */
    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 对话记忆摘要生成线程池
     */
    @Bean
    public Executor memorySummaryThreadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,
                Math.max(2, CPU_COUNT >> 1),
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(200),
                ThreadFactoryBuilder.create()
                        .setNamePrefix("memory_summary_executor_")
                        .build(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return TtlExecutors.getTtlExecutor(executor);
    }
}
