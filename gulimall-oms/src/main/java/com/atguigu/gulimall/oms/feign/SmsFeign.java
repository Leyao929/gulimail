package com.atguigu.gulimall.oms.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("gulimail-sms")
public interface SmsFeign {

    @GetMapping("/hello")
    public String hello();

}


