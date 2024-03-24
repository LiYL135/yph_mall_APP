package com.lyl.yph.product.service;

import com.lyl.yph.model.entity.product.Brand;

import java.util.List;

/**
 * 品牌管理
 */
public interface BrandService {

    // 获取全部品牌
    List<Brand> findAll();

}