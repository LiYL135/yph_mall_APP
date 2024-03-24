package com.lyl.yph.manager.service;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.model.entity.product.Brand;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 品牌管理
 * @Date: 2024/2/1 14:07
 */
public interface BrandService {

    // 分页查询所有品牌
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    // 新增品牌
    void save(Brand brand);

    // 品牌修改，根据id
    void updateById(Brand brand);

    // 删除品牌
    void deleteById(Long id);

    // 查询所有品牌
    List<Brand> findAll();
}
