package com.atguigu.gulimall.commons.to.es;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class EsSKuVo {

    private Long id;//skuid

    private Long brandId;//品牌id

    private String brandName;//品牌名称

    private Long productCategoryId;//三级标题id

    private String productCategoryName;//三级标题名称




    private String pic;//图片

    private String name;//sku标题

    private BigDecimal price;//价格

    private Integer sale;//销量

    private Integer stock;

    private Integer sort;//库存

    //保存当前sku所需要检索的属性
    private List<EsSkuAttributeValue> attrValueList;

}
