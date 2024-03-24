package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.dto.product.ProductDto;
import com.lyl.yph.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 商品管理
 * @Date: 2024/2/1 16:08
 */
@Mapper
public interface ProductMapper {

    //分页查询商品信息
    List<Product> findByPage(ProductDto productDto);

    //保存商品数据
    void save(Product product);

    //根据id查询商品信息
    Product selectById(Long id);

    //修改
    void updateById(Product product);

    //根据id删除商品基本数据
    void deleteById(Long id);
}
