package com.atguigu.gulimall.pms.service;

import com.atguigu.gulimall.pms.vo.AttrSaveVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import java.util.List;


/**
 * 商品属性
 *
 * @author leyao
 * @email hzb@leyao.com
 * @date 2019-08-03 13:21:08
 */
public interface AttrService extends IService<AttrEntity> {

    PageVo queryPage(QueryCondition params);

    PageVo getBaseInfo(QueryCondition queryCondition, Integer catId,Integer type);

    void saveAttrAndAttrGroupRelation(AttrSaveVo attrSaveVo);
}

