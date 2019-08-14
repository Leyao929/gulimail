package com.atguigu.gulimall.pms.vo.detail;

import lombok.Data;

@Data
public class CouponsVo {

    //促销信息 优惠券
    private String name;

    //优惠券类型   0-优惠券  1-满减  2-阶梯
    private Integer type;

}
