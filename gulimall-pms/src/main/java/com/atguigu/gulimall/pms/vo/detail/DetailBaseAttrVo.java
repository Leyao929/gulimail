package com.atguigu.gulimall.pms.vo.detail;

import lombok.Data;

@Data
public class DetailBaseAttrVo {

    //基本属性id
    private Long attrId;

    //基本属性名字
    private String attrName;

    //基本属性的值
    private String[] attrValues;

}
