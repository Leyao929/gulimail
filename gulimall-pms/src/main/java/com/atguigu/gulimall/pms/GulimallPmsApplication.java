package com.atguigu.gulimall.pms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(basePackages = "com.atguigu.gulimall.pms.dao")
@SpringBootApplication
@EnableTransactionManagement
public class GulimallPmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimallPmsApplication.class, args);
    }

}
