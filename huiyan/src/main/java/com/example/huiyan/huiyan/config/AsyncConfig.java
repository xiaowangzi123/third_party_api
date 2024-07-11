package com.example.huiyan.huiyan.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 线程池配置类
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    /**
     * 核心线程数（默认线程数）
     */
    private static final int CORE_POOL_SIZE = 10;

    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 20;

    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    private static final int KEEP_ALIVE_TIME = 60;

    /**
     * 缓冲队列大小
     */
    private static final int QUEUE_CAPACITY = 100;

    /**
     * 线程池名前缀
     */
    private static final String TRANSCRIPTION_THREAD_NAME_PREFIX = "Async-Transcription-Job-";

    /**
     * 线程池名前缀
     */
    private static final String PRODUCTION_THREAD_NAME_PREFIX = "Async-Production-Job-";

    /**
     * 线程池名前缀
     */
    private static final String TRANSACTION_THREAD_NAME_PREFIX = "Async-Transaction-Job-";

    @Bean("transaction")
    public ThreadPoolTaskExecutor shortestJobExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(10);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setThreadNamePrefix(TRANSACTION_THREAD_NAME_PREFIX);

        /**
         * 线程池对拒绝任务的处理策略,这里采用CallerRunsPolicy策略，
         * 当线程池没有处理能力的时候，该策略会直接在execute方法的调用线程中运行被拒绝的任务；
         * 如果执行程序已关闭，则会丢弃该任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();

        /**
         * 该方法用来设置线程池关闭的时候等待所有任务都完成后，再继续销毁其他的Bean，
         * 这样这些异步任务的销毁就会先于数据库连接池对象的销毁。
         */
        executor.setWaitForTasksToCompleteOnShutdown(true);

        /**
         * 该方法用来设置线程池中任务的等待时间，如果超过这个时间还没有销毁就强制销毁，
         * 确保应用最后能够被关闭，而不是阻塞住。
         */
        executor.setAwaitTerminationMillis(120);
        return executor;
    }

    @Bean("transcription")
    public ThreadPoolTaskExecutor transcriptionExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setThreadNamePrefix(TRANSCRIPTION_THREAD_NAME_PREFIX);

        /**
         * 线程池对拒绝任务的处理策略,这里采用CallerRunsPolicy策略，
         * 当线程池没有处理能力的时候，该策略会直接在execute方法的调用线程中运行被拒绝的任务；
         * 如果执行程序已关闭，则会丢弃该任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();

        /**
         * 该方法用来设置线程池关闭的时候等待所有任务都完成后，再继续销毁其他的Bean，
         * 这样这些异步任务的销毁就会先于数据库连接池对象的销毁。
         */
        executor.setWaitForTasksToCompleteOnShutdown(true);

        /**
         * 该方法用来设置线程池中任务的等待时间，如果超过这个时间还没有销毁就强制销毁，
         * 确保应用最后能够被关闭，而不是阻塞住。
         */
        executor.setAwaitTerminationMillis(120);
        return executor;
    }

    /**
     * bean的名称，默认为首字母小写的方法名
     *
     * @return
     */
    @Bean("production")
    public ThreadPoolTaskExecutor productionExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setThreadNamePrefix(PRODUCTION_THREAD_NAME_PREFIX);

        /**
         * 线程池对拒绝任务的处理策略,这里采用CallerRunsPolicy策略，
         * 当线程池没有处理能力的时候，该策略会直接在execute方法的调用线程中运行被拒绝的任务；
         * 如果执行程序已关闭，则会丢弃该任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();

        /**
         * 该方法用来设置线程池关闭的时候等待所有任务都完成后，再继续销毁其他的Bean，
         * 这样这些异步任务的销毁就会先于数据库连接池对象的销毁。
         */
        executor.setWaitForTasksToCompleteOnShutdown(true);

        /**
         * 该方法用来设置线程池中任务的等待时间，如果超过这个时间还没有销毁就强制销毁，
         * 确保应用最后能够被关闭，而不是阻塞住。
         */
        executor.setAwaitTerminationMillis(120);
        return executor;
    }

    @Bean("dubRecognise")
    public ThreadPoolTaskExecutor dubRecogniseExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        executor.setThreadNamePrefix(PRODUCTION_THREAD_NAME_PREFIX);

        /**
         * 线程池对拒绝任务的处理策略,这里采用CallerRunsPolicy策略，
         * 当线程池没有处理能力的时候，该策略会直接在execute方法的调用线程中运行被拒绝的任务；
         * 如果执行程序已关闭，则会丢弃该任务
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();

        /**
         * 该方法用来设置线程池关闭的时候等待所有任务都完成后，再继续销毁其他的Bean，
         * 这样这些异步任务的销毁就会先于数据库连接池对象的销毁。
         */
        executor.setWaitForTasksToCompleteOnShutdown(true);

        /**
         * 该方法用来设置线程池中任务的等待时间，如果超过这个时间还没有销毁就强制销毁，
         * 确保应用最后能够被关闭，而不是阻塞住。
         */
        executor.setAwaitTerminationMillis(120);
        return executor;
    }
}