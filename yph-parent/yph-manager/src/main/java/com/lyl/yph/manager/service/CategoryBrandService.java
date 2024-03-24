package com.lyl.yph.manager.service;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.model.dto.product.CategoryBrandDto;
import com.lyl.yph.model.entity.product.Brand;
import com.lyl.yph.model.entity.product.CategoryBrand;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 分类品牌管理
 * @Date: 2024/2/1 15:03
 */
public interface CategoryBrandService {

    // 分页查询分类品牌关系信息
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    // 添加功能
    void save(CategoryBrand categoryBrand);

    // 修改功能
    void updateById(CategoryBrand categoryBrand);

    // 删除功能
    void deleteById(Long id);

    // 根据分类id获取对应的品牌数据
    List<Brand> findBrandByCategoryId(Long categoryId);
}
