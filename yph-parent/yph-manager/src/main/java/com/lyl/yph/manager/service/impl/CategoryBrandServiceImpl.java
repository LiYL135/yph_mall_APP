package com.lyl.yph.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.yph.manager.mapper.CategoryBrandMapper;
import com.lyl.yph.manager.service.CategoryBrandService;
import com.lyl.yph.model.dto.product.CategoryBrandDto;
import com.lyl.yph.model.entity.product.Brand;
import com.lyl.yph.model.entity.product.CategoryBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper ;

    // 分页查询分类 品牌
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto CategoryBrandDto) {
        PageHelper.startPage(page , limit) ;
        List<CategoryBrand> categoryBrandList = categoryBrandMapper.findByPage(CategoryBrandDto) ;
        return new PageInfo<>(categoryBrandList);
    }

    // 添加功能
    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand) ;
    }

    // 修改功能
    @Override
    public void updateById(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateById(categoryBrand) ;
    }

    // 删除功能
    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id) ;
    }

    // 根据分类id获取对应的品牌数据
    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }

}