package com.atguigu.gulimall.pms.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuVo {

    private String skuName;

    private String skuDesc;

    private String skuTitle;

    private String skuSubtitle;

    private BigDecimal weight;

    private BigDecimal price;

    private String[] images;

    /*
  商品的销售信息
   */
    private SaleAttrVo[] saleAttrs;

    /**
     * 优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]
     */
    @ApiModelProperty(name = "work",value = "优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]")
    private Integer[] work;

    @ApiModelProperty(name = "growBounds",value = "成长积分")
    private BigDecimal growBounds;
    /**
     * 购物积分
     */
    @ApiModelProperty(name = "buyBounds",value = "购物积分")
    private BigDecimal buyBounds;

    /**
     * 满多少
     */
    @ApiModelProperty(name = "fullPrice",value = "满多少")
    private BigDecimal fullPrice;
    /**
     * 减多少
     */
    @ApiModelProperty(name = "reducePrice",value = "减多少")
    private BigDecimal reducePrice;
    /**
     * 是否参与其他优惠
     */
    @ApiModelProperty(name = "addOther",value = "是否参与其他优惠")
    private Integer fullAddOther;


    private Integer ladderAddOther;

    /**
     * 打几折
     */
    @ApiModelProperty(name = "discount",value = "打几折")
    private BigDecimal discount;

    /**
     * 满几件
     */
    @ApiModelProperty(name = "fullCount",value = "满几件")
    private Integer fullCount;





}
