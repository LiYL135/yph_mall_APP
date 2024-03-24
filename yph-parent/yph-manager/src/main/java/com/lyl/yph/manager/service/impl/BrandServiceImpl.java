package com.lyl.yph.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.yph.manager.mapper.BrandMapper;
import com.lyl.yph.manager.service.BrandService;
import com.lyl.yph.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper ;

    /**
     * 分页查询所有品牌
     * @param page
     * @param limit
     * @return
     */
    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Brand> brandList = brandMapper.findByPage() ;
        return new PageInfo(brandList);
    }

    /**
     * 新增品牌
     * @param brand
     */
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand) ;
    }

    /**
     * 品牌修改，根据id
     * @param brand
     */
    @Override
    public void updateById(Brand brand) {
        brandMapper.updateById(brand) ;
    }

    /**
     * 删除品牌
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id) ;
    }

    /**
     * 查询所有品牌
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }

}