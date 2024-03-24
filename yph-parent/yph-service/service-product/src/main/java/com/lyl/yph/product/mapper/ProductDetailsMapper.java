package com.lyl.yph.product.mapper;

import com.lyl.yph.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: lyl
 * @Description: 商品详情
 * @Date: 2024/2/3 10:05
 */
@Mapper
public interface ProductDetailsMapper {

    //根据商品id 获取商品详情信息
    ProductDetails getByProductId(Long productId);

}
