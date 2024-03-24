package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品规格
 */

@Mapper
public interface ProductSpecMapper {

    //分页查询
    public abstract List<ProductSpec> findByPage();

    //添加功能
    void save(ProductSpec productSpec);

    //修改功能
    void updateById(ProductSpec productSpec);

    //删除功能
    void deleteById(Long id);

    //所有哦商品规格
    List<ProductSpec> findAll();
}