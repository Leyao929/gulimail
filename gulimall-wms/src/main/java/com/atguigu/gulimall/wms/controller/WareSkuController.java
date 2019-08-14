package com.atguigu.gulimall.wms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;
import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.commons.to.es.SkuStockVo;
import com.atguigu.gulimall.wms.dao.WareSkuDao;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.wms.entity.WareSkuEntity;
import com.atguigu.gulimall.wms.service.WareSkuService;




/**
 * 商品库存
 *
 * @author leyao
 * @email hzb@leyao.com
 * @date 2019-08-01 21:07:21
 */
@Api(tags = "商品库存 管理")
@RestController
@RequestMapping("wms/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    @Autowired
    private WareSkuDao wareSkuDao;


    @ApiOperation("用于远程调用查询某个sku的库存信息")
    @PostMapping("/skuIds/up")
    public Resp<List<SkuStockVo>> getStock(@RequestBody List<Long> skuIds){

        List<SkuStockVo> stockVos = new ArrayList<>();

        List<WareSkuEntity> wareSkuEntityList = wareSkuDao.selectList(new QueryWrapper<WareSkuEntity>().in("sku_id", skuIds));

        wareSkuEntityList.forEach(wareSkuEntity -> {

            SkuStockVo skuStockVo = new SkuStockVo();

            skuStockVo.setSkuId(wareSkuEntity.getSkuId());

            skuStockVo.setStock(wareSkuEntity.getStock());

            stockVos.add(skuStockVo);

        });

        return Resp.ok(stockVos);

    }

    ///wms/waresku/sku/1

    @ApiOperation("查询某个sku的库存信息")
    @GetMapping("/sku/{skuId}")
    public Resp<List<WareSkuEntity>> getSkuStockBySKuId(@PathVariable Long skuId){

       List<WareSkuEntity> data =  wareSkuService.getSkuStock(skuId);

       return Resp.ok(data);

    }


    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('wms:waresku:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = wareSkuService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('wms:waresku:info')")
    public Resp<WareSkuEntity> info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return Resp.ok(wareSku);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('wms:waresku:save')")
    public Resp<Object> save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('wms:waresku:update')")
    public Resp<Object> update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('wms:waresku:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
