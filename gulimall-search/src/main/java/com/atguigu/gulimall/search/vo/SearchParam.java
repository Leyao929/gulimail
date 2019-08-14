package com.atguigu.gulimall.search.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchParam implements Serializable {

    private String[] catelog3;//三级分类id

    private String[] brand;//品牌id

    private String keyword;//检索的关键字

    private String order;//排序规则

    private Integer pageNum = 1;//分页信息

    private String[] props;//页面提交的数组

    private Integer pageSize = 1;

    private Integer priceFrom; // 价格区间开始

    private Integer priceTo; //价格区间结束



}
