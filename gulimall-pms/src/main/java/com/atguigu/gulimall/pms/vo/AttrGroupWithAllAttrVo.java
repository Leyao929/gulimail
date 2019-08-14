package com.atguigu.gulimall.pms.vo;

import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAllAttrVo extends AttrGroupEntity {

    private List<AttrEntity> attrEntities;

}
