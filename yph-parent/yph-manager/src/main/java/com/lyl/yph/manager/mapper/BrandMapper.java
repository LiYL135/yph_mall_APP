package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 品牌管理
 * @Date: 2024/2/1 14:15
 */

@Mapper
public interface BrandMapper {

    // 分页查询所有品牌
    List<Brand> findByPage();

    // 新增品牌
    void save(Brand brand);

    // 品牌修改，根据id
    void updateById(Brand brand);

    // 删除品牌
    void deleteById(Long id);

    // 查询所有品牌
    List<Brand> findAll();
}
