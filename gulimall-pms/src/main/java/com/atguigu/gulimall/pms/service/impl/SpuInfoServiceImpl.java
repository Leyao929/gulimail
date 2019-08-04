package com.atguigu.gulimall.pms.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.Query;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import com.atguigu.gulimall.pms.dao.SpuInfoDao;
import com.atguigu.gulimall.pms.entity.SpuInfoEntity;
import com.atguigu.gulimall.pms.service.SpuInfoService;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo searchSpuInfo(QueryCondition queryCOndition, String key, Long catId) {

        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();

        if(catId != 0){
            queryWrapper.eq("catalog_id",catId);

            if(!StringUtils.isEmpty(key)){

                queryWrapper.and(obj ->{

                    obj.like("spu_name",key);
                    obj.or().like("id",key);
                    return obj;
                });

            }
        }



        IPage<SpuInfoEntity> page = new Query<SpuInfoEntity>().getPage(queryCOndition);

        IPage<SpuInfoEntity> data = this.page(page, queryWrapper);

        return new PageVo(data);


    }

}