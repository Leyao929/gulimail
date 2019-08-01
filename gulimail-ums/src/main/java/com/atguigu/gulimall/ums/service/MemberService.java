package com.atguigu.gulimall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.ums.entity.MemberEntity;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.QueryCondition;


/**
 * 会员
 *
 * @author leyao
 * @email hzb@leyao.com
 * @date 2019-08-01 20:58:14
 */
public interface MemberService extends IService<MemberEntity> {

    PageVo queryPage(QueryCondition params);
}

