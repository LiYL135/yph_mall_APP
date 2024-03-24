package com.lyl.yph.product.service;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.model.dto.h5.ProductSkuDto;
import com.lyl.yph.model.dto.product.SkuSaleDto;
import com.lyl.yph.model.entity.product.ProductSku;
import com.lyl.yph.model.vo.h5.ProductItemVo;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 首页管理  下半 商品数据
 * @Date: 2024/2/2 14:35
 */
public interface ProductService {

    //商品sku数据
    List<ProductSku> findProductSkuBySale();

    //分页查询 商品信息
    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    //商品详情
    ProductItemVo item(Long skuId);

    //获取商品sku信息
    ProductSku getBySkuId(Long skuId);

    //更新商品销量、数量
    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}
