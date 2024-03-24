package com.lyl.yph.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.yph.model.dto.h5.ProductSkuDto;
import com.lyl.yph.model.dto.product.SkuSaleDto;
import com.lyl.yph.model.entity.product.Product;
import com.lyl.yph.model.entity.product.ProductDetails;
import com.lyl.yph.model.entity.product.ProductSku;
import com.lyl.yph.model.vo.h5.ProductItemVo;
import com.lyl.yph.product.mapper.ProductDetailsMapper;
import com.lyl.yph.product.mapper.ProductMapper;
import com.lyl.yph.product.mapper.ProductSkuMapper;
import com.lyl.yph.product.service.ProductService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//商品
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    //畅销商品列表，按销售
    @Override
    public List<ProductSku> findProductSkuBySale() {
        return productSkuMapper.findProductSkuBySale();
    }

    //分页查询 商品sku信息
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page, limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(productSkuList);
    }

    // 商品详情信息
    @Override
    public ProductItemVo item(Long skuId) {
        //根据skuId 获取当前sku信息
        ProductSku productSku = productSkuMapper.getById(skuId);

        //根据sku信息中的商品id 获取当前商品信息
        Product product = productMapper.getById(productSku.getProductId());

        //同一个商品下面的sku信息列表
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productSku.getProductId());
        //建立sku规格与skuId对应关系
        Map<String,Object> skuSpecValueMap = new HashMap<>();
        productSkuList.forEach(item -> {
            skuSpecValueMap.put(item.getSkuSpec(), item.getId()); //商品skuspec
        });

        //商品详情信息
        ProductDetails productDetails = productDetailsMapper.getByProductId(productSku.getProductId());

        ProductItemVo productItemVo = new ProductItemVo();
        productItemVo.setProductSku(productSku); //商品sku
        productItemVo.setProduct(product); //商品信息
        //详情图片 list集合
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));//轮播图 list集合
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue())); //商品规格
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);
        return productItemVo;
    }

    //获取商品sku信息
    @Override
    public ProductSku getBySkuId(Long skuId) {
        return productSkuMapper.getById(skuId);
    }

    //更新商品销量、数量
    @Transactional
    @Override
    public Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList) {
        if(!CollectionUtils.isEmpty(skuSaleDtoList)) {
            for(SkuSaleDto skuSaleDto : skuSaleDtoList) {
                productSkuMapper.updateSale(skuSaleDto.getSkuId(), skuSaleDto.getNum());
            }
        }
        return true;
    }

}