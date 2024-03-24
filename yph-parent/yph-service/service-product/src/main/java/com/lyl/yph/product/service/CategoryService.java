package com.lyl.yph.product.service;

import com.lyl.yph.model.entity.product.Category;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 首页接口管理 上半 -- 商品分类
 * @Date: 2024/2/2 14:34
 */
public interface CategoryService {

    //查询一级分类数据
    List<Category> findOneCategory();

    //获取分类树形数据
    List<Category> findCategoryTree();
}
