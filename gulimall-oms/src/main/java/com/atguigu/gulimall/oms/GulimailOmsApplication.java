package com.atguigu.gulimall.oms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "com.atguigu.oms.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GulimailOmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailOmsApplication.class, args);
    }

}
