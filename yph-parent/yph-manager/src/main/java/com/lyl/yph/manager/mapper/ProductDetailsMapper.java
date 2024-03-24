package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: lyl
 * @Description: 商品详情
 * @Date: 2024/2/1 17:11
 */
@Mapper
public interface ProductDetailsMapper {

    //保存商品详情数据
    void save(ProductDetails productDetails);

    //根据商品的id查询商品详情数据
    ProductDetails selectByProductId(Long id);

    //修改
    void updateById(ProductDetails productDetails);

    //根据商品的id删除商品的详情数据
    void deleteByProductId(Long id);
}
