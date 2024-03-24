package com.lyl.yph.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.yph.manager.mapper.ProductSpecMapper;
import com.lyl.yph.manager.service.ProductSpecService;
import com.lyl.yph.model.entity.product.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品规格
 */

@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper ;

    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page , limit) ;
        List<ProductSpec> productSpecList = productSpecMapper.findByPage() ;
        return new PageInfo<>(productSpecList);
    }

    /**
     * 添加商品规格
     * @param productSpec
     */
    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec) ;
    }

    /**
     * 修改功能
     * @param productSpec
     */
    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.updateById(productSpec);
    }

    /**
     * 删除功能
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        productSpecMapper.deleteById(id);
    }

    /**
     * 所有商品规格
     * @return
     */
    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findAll();
    }

}