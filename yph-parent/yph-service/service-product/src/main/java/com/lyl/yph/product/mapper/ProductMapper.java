package com.lyl.yph.product.mapper;

import com.lyl.yph.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: lyl
 * @Description: 商品信息
 * @Date: 2024/2/3 9:58
 */
@Mapper
public interface ProductMapper {

    //根据商品id获取商品信息
    Product getById(Long productId);
}
