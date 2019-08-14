package com.atguigu.gulimall.pms.controller.api;

import com.atguigu.gulimall.commons.bean.Resp;
import com.atguigu.gulimall.pms.annotation.GuliCache;
import com.atguigu.gulimall.pms.service.CategoryService;
import com.atguigu.gulimall.pms.vo.CategoryWithChildrensVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {


    @Autowired
    private CategoryService categoryService;


    @ApiOperation("获取所有的分类")
    @GuliCache(prefix = "cache:category")
    @GetMapping("/cates")
    public Resp<Object> level1Catelogs(){


        List<CategoryWithChildrensVo> childrens = categoryService.getCategoryChildrensAndSubsById(0);
        return Resp.ok(childrens);
    }


    @ApiOperation("获取所有的一级分类")
    @GuliCache(prefix = "cache:category")
    @GetMapping("/cates/{id}")
    public Resp<Object> catelogChildrens(@PathVariable("id")Integer id){

        List<CategoryWithChildrensVo> childrens = categoryService.getCategoryChildrensAndSubsById(id);
        return Resp.ok(childrens);
    }

}
