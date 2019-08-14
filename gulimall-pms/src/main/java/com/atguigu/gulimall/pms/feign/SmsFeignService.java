package com.atguigu.gulimall.pms.feign;

import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.commons.to.SkuSaleInfoTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient("gulimall-sms")
public interface SmsFeignService {

    @ResponseBody
    @PostMapping("/sms/skubounds/saleInfo/save")
    public Resp<Object> saveSaleInfo(@RequestBody List<SkuSaleInfoTo> skuSaleInfoTos);


}
