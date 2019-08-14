package com.atguigu.gulimall.pms.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;
import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.pms.vo.ProductSaveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.pms.entity.SpuInfoEntity;
import com.atguigu.gulimall.pms.service.SpuInfoService;




/**
 * spu信息
 *
 * @author leyao
 * @email hzb@leyao.com
 * @date 2019-08-03 13:21:08
 */
@Api(tags = "spu信息 管理")
@RestController
@RequestMapping("/pms/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;


    /**
     * /pms/spuinfo/updateStatus/{spuId}
     */
    @ApiOperation("商品上架下架")
    @GetMapping("/updateStatus/{spuId}")
    public Resp<Object> updateSpuStatu(@RequestParam("status")Integer status,@PathVariable("spuId")Long spuId){


        spuInfoService.updateSpuStatu(status,spuId);

        return Resp.ok(null);
    }


    ///pms/spuinfo/simple/search?t=1564820547765&page=1&limit=10&key=&catId=0
    @ApiOperation("按照spuid,spuname,分类id检索商品")
    @GetMapping("/simple/search")
    public Resp<PageVo> spuInfoList(QueryCondition queryCOndition,String key,Long catId){

        if(catId == null){
            return Resp.fail(null);

        }

        PageVo pageVo = spuInfoService.searchSpuInfo(queryCOndition, key, catId);

        return Resp.ok(pageVo);
    }

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:spuinfo:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = spuInfoService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:spuinfo:info')")
    public Resp<SpuInfoEntity> info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return Resp.ok(spuInfo);
    }

    /**
     * 保存  /pms/spuinfo/save  保存一个商品信息
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:spuinfo:save')")
    public Resp<Object> save(@RequestBody ProductSaveVo productSaveVo){

        //实现商品信息保存功能

        spuInfoService.insert(productSaveVo);



        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:spuinfo:update')")
    public Resp<Object> update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:spuinfo:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
