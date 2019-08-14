package com.atguigu.gulimall.pms.config;

import io.netty.util.concurrent.SingleThreadEventExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * 这是一个线程池的配置类
 */
@Configuration
public class ThreadPoolConfig {

    @Bean("mainThreadPool")
    public ThreadPoolExecutor threadPoolExecutor(@Value("${main.threadpool.coresize}")Integer core,@Value("${main.threadpool.maxsize}")Integer max){

//        int corePoolSize,
//        int maximumPoolSize,
//        long keepAliveTime,
//        TimeUnit unit,
//        BlockingQueue<Runnable> workQueue


        return new ThreadPoolExecutor(core,max,30,TimeUnit.SECONDS,new LinkedBlockingQueue<>(Integer.MAX_VALUE/2));

    }

}
