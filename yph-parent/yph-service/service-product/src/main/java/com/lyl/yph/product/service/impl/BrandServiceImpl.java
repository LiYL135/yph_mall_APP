package com.lyl.yph.product.service.impl;

import com.lyl.yph.model.entity.product.Brand;
import com.lyl.yph.product.mapper.BrandMapper;
import com.lyl.yph.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌管理
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    // 获取全部品牌
    @Cacheable(value = "brandList", unless="#result.size() == 0")
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

}