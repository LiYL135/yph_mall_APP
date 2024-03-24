package com.lyl.yph.product.mapper;

import com.lyl.yph.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 品牌管理
 */
@Mapper
public interface BrandMapper {

    // 获取全部品牌
    List<Brand> findAll();

}