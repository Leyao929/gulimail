package com.atguigu.gulimall.oms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(basePackages = "com.atguigu.oms.dao")
@EnableSwagger2
@SpringBootApplication
public class GulimailOmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailOmsApplication.class, args);
    }

}
