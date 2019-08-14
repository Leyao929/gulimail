package com.atguigu.gulimall.pms.vo.detail;

import lombok.Data;

@Data
public class DetailSaleAttrVo {

    //销售属性的id
    private Long attrId;

    //销售属性的名字
    private String attrName;

    //销售属性的值
    private String[] attrValues;

}
