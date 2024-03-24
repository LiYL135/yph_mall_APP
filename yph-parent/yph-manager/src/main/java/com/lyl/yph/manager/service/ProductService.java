package com.lyl.yph.manager.service;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.model.dto.product.ProductDto;
import com.lyl.yph.model.entity.product.Product;

/**
 * @Author: lyl
 * @Description: 商品管理
 * @Date: 2024/2/1 16:07
 */
public interface ProductService {

    //分页查询
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

    //保存商品数据
    void save(Product product);

    //根据id查询商品详情
    Product getById(Long id);

    //保存修改数据
    void updateById(Product product);

    //删除商品
    void deleteById(Long id);

    //商品审核
    void updateAuditStatus(Long id, Integer auditStatus);

    //商品 上架 |下架
    void updateStatus(Long id, Integer status);
}
