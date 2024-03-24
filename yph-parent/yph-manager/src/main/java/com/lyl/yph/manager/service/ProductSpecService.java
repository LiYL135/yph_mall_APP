package com.lyl.yph.manager.service;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.model.entity.product.ProductSpec;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 商品规格
 * @Date: 2024/2/1 15:47
 */
public interface ProductSpecService {

    //分页查询
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);

    //添加商品规格
    void save(ProductSpec productSpec);

    //修改功能
    void updateById(ProductSpec productSpec);

    //删除功能
    void deleteById(Long id);

    //所有商品规格
    List<ProductSpec> findAll();
}
