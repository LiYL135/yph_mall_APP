package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.dto.product.CategoryBrandDto;
import com.lyl.yph.model.entity.product.Brand;
import com.lyl.yph.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {

    // 分页查询分类品牌关系信息
    public abstract List<CategoryBrand> findByPage(CategoryBrandDto CategoryBrandDto);

    // 添加功能
    void save(CategoryBrand categoryBrand);

    // 修改功能
    void updateById(CategoryBrand categoryBrand);

    // 删除功能
    void deleteById(Long id);

    // 根据分类id获取对应的品牌数据
    List<Brand> findBrandByCategoryId(Long categoryId);
}