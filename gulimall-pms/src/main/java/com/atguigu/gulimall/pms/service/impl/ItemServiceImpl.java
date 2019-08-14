package com.atguigu.gulimall.pms.service.impl;

import com.atguigu.gulimall.pms.entity.SkuImagesEntity;
import com.atguigu.gulimall.pms.entity.SkuInfoEntity;
import com.atguigu.gulimall.pms.entity.SpuInfoDescEntity;
import com.atguigu.gulimall.pms.service.*;
import com.atguigu.gulimall.pms.vo.SkuItemDetailVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    @Qualifier("mainThreadPool")
    private ThreadPoolExecutor mainThreadPool;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Override
    public SkuItemDetailVo getDetail(Long skuId) throws ExecutionException, InterruptedException {


        SkuItemDetailVo skuItemDetailVo = new SkuItemDetailVo();
        //1、查询sku的基本信息
        CompletableFuture<SkuInfoEntity> skuInfoFuture = CompletableFuture.supplyAsync(() -> {

            SkuInfoEntity skuInfoEntity = skuInfoService.getById(skuId);

            return skuInfoEntity;
        }, mainThreadPool);

        //开启一个异步任务处理返回结果
        CompletableFuture<Void> skuInfoAccept = skuInfoFuture.thenAcceptAsync((skuInfo) -> {
            BeanUtils.copyProperties(skuInfo, skuItemDetailVo);
        },mainThreadPool);


        //2、sku的所有图片
        CompletableFuture<List<SkuImagesEntity>> skuImagesFuture = CompletableFuture.supplyAsync(() -> {

            List<SkuImagesEntity> images = skuImagesService.list(new QueryWrapper<SkuImagesEntity>().eq("sku_id", skuId));
            return images;
        },mainThreadPool);

        CompletableFuture<Void> imagesAccept = skuImagesFuture.thenAcceptAsync((skuImages) -> {

            List<String> list = new ArrayList<>();

            if (skuImages != null && skuImages.size() > 0) {
                for (SkuImagesEntity skuImage : skuImages) {

                    list.add(skuImage.getImgUrl());

                }

                skuItemDetailVo.setPics(list);
            }

        },mainThreadPool);

        //3、sku的所有促销信息   2s


        //4、sku的所有销售属性组合   2s

        //5、spu的所有基本属性  1s

        //6、详情介绍  1s  等skuInfo查询完再查询
        CompletableFuture<Void> spuFuture = skuInfoFuture.thenAcceptAsync((skuInfo) -> {

            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(skuInfo.getSpuId());

            skuItemDetailVo.setDesc(spuInfoDescEntity);
        },mainThreadPool);


        //统一等待
        CompletableFuture<Void> allOf = CompletableFuture.allOf(skuInfoFuture, skuInfoAccept, skuImagesFuture, imagesAccept, spuFuture);

        //阻塞获取结果
        allOf.get();


        return skuItemDetailVo;
    }
}
