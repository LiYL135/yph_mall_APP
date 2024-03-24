package com.lyl.yph.feign.product;

import com.lyl.yph.model.dto.product.SkuSaleDto;
import com.lyl.yph.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 针对service-product微服务的远程调用接口
 */
@FeignClient(value = "service-product") //远程调用所在的文件
public interface ProductFeignClient {

    //根据skuId获取sku信息
    @GetMapping("/api/product/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable("skuId") Long skuId);

    //更新商品销量、数量
    @PostMapping("/api/product/updateSkuSaleNum")
    Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList);

}