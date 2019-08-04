package com.atguigu.gulimall.pms.service.impl;

import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import com.atguigu.gulimall.pms.service.AttrGroupService;
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

import com.atguigu.gulimall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.pms.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Autowired
    private AttrGroupService attrGroupService;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public List<AttrAttrgroupRelationEntity> selectByGroupId(Integer attrGroupId) {

        QueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("attr_group_id",attrGroupId);


        return baseMapper.selectList(queryWrapper);

    }

    @Override
    public void deleteByRelations(AttrAttrgroupRelationEntity[] realtions) {

        for (AttrAttrgroupRelationEntity realtion : realtions) {

            QueryWrapper<AttrAttrgroupRelationEntity> queryWrapper = new QueryWrapper<>();

            queryWrapper.eq("attr_id",realtion.getAttrId()).eq("attr_group_id",realtion.getAttrGroupId());

            baseMapper.delete(queryWrapper);

        }

    }

    @Override
    public AttrGroupEntity selectGroupByAttrId(Long attrId) {

        QueryWrapper<AttrAttrgroupRelationEntity> relationWrapper = new QueryWrapper<>();

        relationWrapper.eq("attr_id",attrId);

        AttrAttrgroupRelationEntity attrgroupRelationEntity = baseMapper.selectOne(relationWrapper);

        AttrGroupEntity groupEntity = null;

        if(attrgroupRelationEntity!=null) {

            groupEntity = attrGroupService.getById(attrgroupRelationEntity.getAttrGroupId());
        }

        return groupEntity;

    }

}