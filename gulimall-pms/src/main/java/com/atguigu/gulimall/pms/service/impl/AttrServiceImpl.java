package com.atguigu.gulimall.pms.service.impl;

import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.pms.vo.AttrSaveVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.Query;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import com.atguigu.gulimall.pms.dao.AttrDao;
import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.pms.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo getBaseInfo(QueryCondition queryCondition, Integer catId,Integer type) {

        //根据catId查询基本信息
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();

        IPage<AttrEntity> data = null;

        if(catId != null){
            queryWrapper.eq("catelog_id",catId).eq("attr_type",type);

            IPage<AttrEntity> page = new Query<AttrEntity>().getPage(queryCondition);

             data = this.page(page, queryWrapper);
        }



        return new PageVo(data);

    }

    @Override
    @Transactional
    public void saveAttrAndAttrGroupRelation(AttrSaveVo attrSaveVo) {

        AttrEntity attr = new AttrEntity();

        BeanUtils.copyProperties(attrSaveVo,attr);

        attrDao.insert(attr);

        //保存关联关系
        Long attrId = attr.getAttrId();

        //System.out.println(attrId);

        AttrAttrgroupRelationEntity attrgroupRelationEntity = new AttrAttrgroupRelationEntity();

        attrgroupRelationEntity.setAttrId(attrId);

        attrgroupRelationEntity.setAttrGroupId(attrSaveVo.getAttrGroupId());

        attrAttrgroupRelationDao.insert(attrgroupRelationEntity);


    }


}