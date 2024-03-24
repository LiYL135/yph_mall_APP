package com.lyl.yph.manager.service.impl;

import com.lyl.yph.manager.mapper.ProductUnitMapper;
import com.lyl.yph.manager.service.ProductUnitService;
import com.lyl.yph.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: lyl
 * @Description:
 * @Date: 2024/2/1 16:50
 */
@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper;

    /**
     * 查询所有商品单元
     * @return
     */
    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll() ;
    }

}
