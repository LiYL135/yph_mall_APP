package com.lyl.yph.product.mapper;

import com.lyl.yph.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 商品分类
 * @Date: 2024/2/2 14:47
 */
@Mapper
public interface CategoryMapper {

    //查询一级分类数据
    List<Category> findOneCategory();

    //全部分类
    List<Category> findAll();
}
