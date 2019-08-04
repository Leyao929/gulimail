package com.atguigu.gulimall.pms.service;

import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import java.util.List;


/**
 * 属性&属性分组关联
 *
 * @author leyao
 * @email hzb@leyao.com
 * @date 2019-08-03 13:21:08
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageVo queryPage(QueryCondition params);

    List<AttrAttrgroupRelationEntity> selectByGroupId(Integer attrGroupId);

    void deleteByRelations(AttrAttrgroupRelationEntity[] realtions);

    AttrGroupEntity selectGroupByAttrId(Long attrId);
}

