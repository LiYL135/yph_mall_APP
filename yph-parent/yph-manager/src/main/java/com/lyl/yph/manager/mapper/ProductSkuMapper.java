package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 商品的sku
 * @Date: 2024/2/1 17:08
 */
@Mapper
public interface ProductSkuMapper {

    //保存商品的sku数据
    void save(ProductSku productSku);

    //根据id查询商品sku
    List<ProductSku> selectByProductId(Long id);

    //修改
    void updateById(ProductSku productSku);

    //根据商品id删除商品的sku数据
    void deleteByProductId(Long id);
}
