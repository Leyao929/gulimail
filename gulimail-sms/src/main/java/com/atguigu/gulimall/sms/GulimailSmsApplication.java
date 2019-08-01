package com.atguigu.gulimall.sms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.atguigu.gulimall.sms.dao")
@EnableSwagger2
public class GulimailSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailSmsApplication.class, args);
    }

}
