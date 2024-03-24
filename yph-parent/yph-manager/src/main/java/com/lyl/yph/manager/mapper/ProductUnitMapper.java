package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 商品单元
 * @Date: 2024/2/1 16:52
 */
@Mapper
public interface ProductUnitMapper {

    // 查询所有商品单元数据
    List<ProductUnit> findAll();

}
