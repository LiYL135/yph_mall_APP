package com.lyl.yph.manager.service;

import com.lyl.yph.model.entity.base.ProductUnit;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 商品单元数据
 * @Date: 2024/2/1 16:47
 */
public interface ProductUnitService {

    //查询所有商品单元
    List<ProductUnit> findAll();
}
