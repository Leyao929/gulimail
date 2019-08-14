package com.atguigu.gulimall.sms.service.impl;

import com.atguigu.gulimall.commons.to.SkuSaleInfoTo;
import com.atguigu.gulimall.sms.dao.SkuFullReductionDao;
import com.atguigu.gulimall.sms.dao.SkuLadderDao;
import com.atguigu.gulimall.sms.entity.SkuFullReductionEntity;
import com.atguigu.gulimall.sms.entity.SkuLadderEntity;
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

import com.atguigu.gulimall.sms.dao.SkuBoundsDao;
import com.atguigu.gulimall.sms.entity.SkuBoundsEntity;
import com.atguigu.gulimall.sms.service.SkuBoundsService;
import org.springframework.transaction.annotation.Transactional;


@Service("skuBoundsService")
public class SkuBoundsServiceImpl extends ServiceImpl<SkuBoundsDao, SkuBoundsEntity> implements SkuBoundsService {

    @Autowired
    private SkuLadderDao skuLadderDao;

    @Autowired
    private SkuFullReductionDao skuFullReductionDao;

    @Autowired
    private SkuBoundsDao skuBoundsDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SkuBoundsEntity> page = this.page(
                new Query<SkuBoundsEntity>().getPage(params),
                new QueryWrapper<SkuBoundsEntity>()
        );

        return new PageVo(page);
    }


    @Override
    public void saveSaleInfo(List<SkuSaleInfoTo> skuSaleInfoTos) {

        //拿到数据
        if(skuSaleInfoTos !=null && skuSaleInfoTos.size() > 0){

            for (SkuSaleInfoTo skuSaleInfoTo : skuSaleInfoTos) {

                SkuLadderEntity skuLadderEntity = new SkuLadderEntity();

                skuLadderEntity.setSkuId(skuSaleInfoTo.getSkuId());

                skuLadderEntity.setFullCount(skuSaleInfoTo.getFullCount());

                skuLadderEntity.setDiscount(skuSaleInfoTo.getDiscount());

                skuLadderEntity.setAddOther(skuSaleInfoTo.getLadderAddOther());

                skuLadderDao.insert(skuLadderEntity);

                //往full reduce表中插入数据
                SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();

                skuFullReductionEntity.setAddOther(skuSaleInfoTo.getFullAddOther());

                skuFullReductionEntity.setFullPrice(skuSaleInfoTo.getFullPrice());

                skuFullReductionEntity.setSkuId(skuSaleInfoTo.getSkuId());

                skuFullReductionEntity.setReducePrice(skuSaleInfoTo.getReducePrice());

                skuFullReductionDao.insert(skuFullReductionEntity);

                //往bound表中加入数据
                SkuBoundsEntity skuBoundsEntity = new SkuBoundsEntity();

                skuBoundsEntity.setSkuId(skuSaleInfoTo.getSkuId());

                skuBoundsEntity.setBuyBounds(skuSaleInfoTo.getBuyBounds());

                skuBoundsEntity.setGrowBounds(skuSaleInfoTo.getGrowBounds());

                Integer[] work = skuSaleInfoTo.getWork();

                Integer i = work[3]*1 + work[2]*2 +work[1]*4 +work[0]*8 ;

                skuBoundsEntity.setWork(i);

                skuBoundsDao.insert(skuBoundsEntity);

            }

        }

    }

}