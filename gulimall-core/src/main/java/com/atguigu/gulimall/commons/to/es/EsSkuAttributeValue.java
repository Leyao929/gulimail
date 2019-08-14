package com.atguigu.gulimall.commons.to.es;

import lombok.Data;

@Data
public class EsSkuAttributeValue  {

    private Long id; //商品和属性关联的数据表的主键id

    //当前sku对应的属性id
    private Long productAttributeId;

    //属性名
    private String name;

    private String value;//属性值

    private Long spuId;//这个属性关系对应的spu的id





}
