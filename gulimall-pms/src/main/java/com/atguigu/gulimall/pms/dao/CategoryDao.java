package com.atguigu.gulimall.pms.dao;

import com.atguigu.gulimall.pms.entity.CategoryEntity;
import com.atguigu.gulimall.pms.vo.CategoryWithChildrensVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品三级分类
 * 
 * @author leyao
 * @email hzb@leyao.com
 * @date 2019-08-03 13:21:08
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {


    List<CategoryWithChildrensVo> getCategoryChildrensAndSubsById(@Param("id")Integer i);

    //List<CategoryWithChildrensVo> selectCategoryChildrenWithChildrens(Integer id);
}
