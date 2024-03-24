package com.lyl.yph.product.mapper;

import com.lyl.yph.model.dto.h5.ProductSkuDto;
import com.lyl.yph.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 商品
 * @Date: 2024/2/2 14:58
 */
@Mapper
public interface ProductSkuMapper {

    //畅销商品列表，按销售
    List<ProductSku> findProductSkuBySale();

    //分页查询 商品sku信息
    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    //根据skuId 获取当前sku信息
    ProductSku getById(Long skuId);

    //根据商品id 获取当前sku信息
    List<ProductSku> findByProductId(Long productId);

    //更新商品销量、数量
    void updateSale(@Param("skuId") Long skuId, @Param("num") Integer num);
}
