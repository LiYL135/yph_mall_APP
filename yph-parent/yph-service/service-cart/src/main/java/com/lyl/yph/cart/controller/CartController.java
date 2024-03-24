package com.lyl.yph.cart.controller;

import com.lyl.yph.cart.service.CartService;
import com.lyl.yph.model.entity.h5.CartInfo;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 购物车 接口
 * @Date: 2024/2/3 20:11
 */
@RestController
@RequestMapping("api/order/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //添加到购物车
    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable("skuId") Long skuId,
                            @Parameter(name = "skuNum", description = "数量", required = true) @PathVariable("skuNum") Integer skuNum) {
        cartService.addToCart(skuId, skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //查询购物车
    @GetMapping("auth/cartList")
    public Result<List<CartInfo>> cartList() {
        List<CartInfo> cartInfoList = cartService.getCartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }

    //删除购物车商品
    @DeleteMapping("auth/deleteCart/{skuId}")
    public Result deleteCart(@Parameter(name = "skuId", description = "商品skuId", required = true)
                                 @PathVariable("skuId") Long skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //更新购物车商品选中状态
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable(value = "skuId") Long skuId,
                            @Parameter(name = "isChecked", description = "是否选中 1:选中 0:取消选中", required = true) @PathVariable(value = "isChecked") Integer isChecked) {
        cartService.checkCart(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //更新购物车商品全部选中状态
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@Parameter(name = "isChecked", description = "是否选中 1:选中 0:取消选中", required = true) @PathVariable(value = "isChecked") Integer isChecked){
        cartService.allCheckCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //清空购物车
    @GetMapping("/auth/clearCart")
    public Result clearCart(){
        cartService.clearCart();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //远程调用：获取购物项数据 - 选中的购物车 ---订单结算使用
    @GetMapping(value = "/auth/getAllCkecked")
    public List<CartInfo> getAllCkecked() {
        List<CartInfo> cartInfoList = cartService.getAllCkecked() ;
        return cartInfoList;
    }

    //远程调用--删除生成订单的购物车商品
    @GetMapping("/auth/deleteChecked")
    public Result deleteChecked(){
        cartService.deleteChecked();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
