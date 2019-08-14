package com.atguigu.gulimall.commons.to.es;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkuStockVo {

    @ApiModelProperty(name = "skuId",value = "sku_id")
    private Long skuId;

    /**
     * 库存数
     */
    @ApiModelProperty(name = "stock",value = "库存数")
    private Integer stock;
}
