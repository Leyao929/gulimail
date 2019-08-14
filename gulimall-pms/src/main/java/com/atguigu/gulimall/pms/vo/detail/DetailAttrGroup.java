package com.atguigu.gulimall.pms.vo.detail;

import lombok.Data;

import java.util.List;

@Data
public class DetailAttrGroup {
    //属性分组的id
    private Long id;

    //属性分组的名字
    private String name;

    //属性分组下的所有基本属性
    private List<DetailBaseAttrVo> attrs;

}
