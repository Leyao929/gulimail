package com.atguigu.gulimall.oms.controller;

import com.atguigu.gulimall.oms.feign.SmsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloController {

    @Autowired
    private SmsFeign smsFeign;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${redis}")
    String redis;

    @GetMapping("/helloFeign")
    public String helloFeign(){

        String msg = "";

        msg = smsFeign.hello();

        return "hello" + msg + "url=" + url + "redis ==" + redis;

    }


}
