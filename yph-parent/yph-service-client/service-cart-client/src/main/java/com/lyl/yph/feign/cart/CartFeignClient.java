package com.lyl.yph.feign.cart;

import com.lyl.yph.model.entity.h5.CartInfo;
import com.lyl.yph.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 远程openFeign接口
 */
@FeignClient(value = "service-cart")
public interface CartFeignClient {

    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    public List<CartInfo> getAllCkecked() ;

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    public Result deleteChecked() ;

}