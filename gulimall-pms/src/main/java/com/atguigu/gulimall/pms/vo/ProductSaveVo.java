package com.atguigu.gulimall.pms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("封装添加spu信息的对象")
@Data
public class ProductSaveVo {

    @ApiModelProperty(name = "spuName",value = "商品名称")
    private String spuName;
    /**
     * 商品描述
     */
    @ApiModelProperty(name = "spuDescription",value = "商品描述")
    private String spuDescription;
    /**
     * 所属分类id
     */
    @ApiModelProperty(name = "catalogId",value = "所属分类id")
    private Long catalogId;
    /**
     * 品牌id
     */
    @ApiModelProperty(name = "brandId",value = "品牌id")
    private Long brandId;
    /**
     * 上架状态[0 - 下架，1 - 上架]
     */
    @ApiModelProperty(name = "publishStatus",value = "上架状态[0 - 下架，1 - 上架]")
    private Integer publishStatus;

    /*
    商品的图片信息
     */
    private String[] spuImages;

    /*
    商品的基本信息
     */
    private BaseAttrVo[] baseAttrs;


    private SkuVo[] skus;

}
