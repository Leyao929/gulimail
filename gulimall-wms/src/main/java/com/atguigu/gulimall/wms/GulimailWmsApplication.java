package com.atguigu.gulimall.wms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.atguigu.gulimall.wms.dao")
public class GulimailWmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GulimailWmsApplication.class, args);
    }

}
