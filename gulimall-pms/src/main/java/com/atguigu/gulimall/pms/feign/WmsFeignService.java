package com.atguigu.gulimall.pms.feign;

import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.commons.to.es.SkuStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("gulimall-wms")
public interface WmsFeignService {

    @PostMapping("/wms/waresku/skuIds/up")
    public Resp<List<SkuStockVo>> getStock(@RequestBody List<Long> skuIds);
}
