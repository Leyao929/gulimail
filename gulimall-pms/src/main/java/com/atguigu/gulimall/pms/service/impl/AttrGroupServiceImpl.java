package com.atguigu.gulimall.pms.service.impl;

import com.atguigu.gulimall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.pms.dao.AttrDao;
import com.atguigu.gulimall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.pms.entity.AttrEntity;
import com.atguigu.gulimall.pms.vo.AttrGroupWithAllAttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.Query;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import com.atguigu.gulimall.pms.dao.AttrGroupDao;
import com.atguigu.gulimall.pms.entity.AttrGroupEntity;
import com.atguigu.gulimall.pms.service.AttrGroupService;
import org.w3c.dom.Attr;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    AttrDao attrDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo selectgroupInfo(QueryCondition queryCondition, Integer catId) {

        //1、获取封装的分页条件
        IPage<AttrGroupEntity> page = new Query<AttrGroupEntity>().getPage(queryCondition);

        QueryWrapper<AttrGroupEntity> groupEntityQueryWrapper = new QueryWrapper<>();


            //查出此三级分类下的属性分组
            groupEntityQueryWrapper.eq("catelog_id",catId);

            IPage<AttrGroupEntity> attrGroupEntityIPage = this.page(page, groupEntityQueryWrapper);

            List<AttrGroupEntity> attrGroupEntityList = attrGroupEntityIPage.getRecords();

            //创建出需要返回的数据集合
            List<AttrGroupWithAllAttrVo> list = new ArrayList<>();

            if(attrGroupEntityList != null && attrGroupEntityList.size() > 0){

                //遍历属性分组集合根据分组id查询关联表得到属性Id集合
                for (AttrGroupEntity groupEntity : attrGroupEntityList) {

                    //为返回对象的部分数据赋值
                    AttrGroupWithAllAttrVo attrGroupWithAllAttrVo = new AttrGroupWithAllAttrVo();

                    BeanUtils.copyProperties(groupEntity,attrGroupWithAllAttrVo);

                    //根据分组Id查询关联表
                    List<AttrAttrgroupRelationEntity> attrgroupRelationEntityList = attrAttrgroupRelationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", groupEntity.getAttrGroupId()));


                    if(attrgroupRelationEntityList != null && attrgroupRelationEntityList.size() > 0){


                        List<Long> attrIdList = new ArrayList<>();

                        for (AttrAttrgroupRelationEntity attrgroupRelationEntity : attrgroupRelationEntityList) {

                            attrIdList.add(attrgroupRelationEntity.getAttrId());

                        }

                        //根据attrId集合查询属性表
                        List<AttrEntity> entities = attrDao.selectList(new QueryWrapper<AttrEntity>().in("attr_id", attrIdList));

                        attrGroupWithAllAttrVo.setAttrEntities(entities);

                    }

                    list.add(attrGroupWithAllAttrVo);
                }
            }



        return new PageVo(list,attrGroupEntityIPage.getTotal(),attrGroupEntityIPage.getSize(),attrGroupEntityIPage.getCurrent());

    }

}