package com.atguigu.gulimall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.sms.entity.SeckillSkuNoticeEntity;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;


/**
 * 秒杀商品通知订阅
 *
 * @author leyao
 * @email hzb@leyao.com
 * @date 2019-08-01 20:51:09
 */
public interface SeckillSkuNoticeService extends IService<SeckillSkuNoticeEntity> {

    PageVo queryPage(QueryCondition params);
}

