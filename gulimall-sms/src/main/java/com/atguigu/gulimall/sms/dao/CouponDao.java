package com.atguigu.gulimall.sms.dao;

import com.atguigu.gulimall.sms.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author leyao
 * @email hzb@leyao.com
 * @date 2019-08-03 13:28:32
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
