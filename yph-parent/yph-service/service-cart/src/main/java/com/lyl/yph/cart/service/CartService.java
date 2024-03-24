package com.lyl.yph.cart.service;

import com.lyl.yph.model.entity.h5.CartInfo;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 购物车
 * @Date: 2024/2/3 20:13
 */
public interface CartService {

    //添加到购物车
    void addToCart(Long skuId, Integer skuNum);

    //查询购物车
    List<CartInfo> getCartList();

    //删除购物车商品
    void deleteCart(Long skuId);

    //更新购物车商品选中状态
    void checkCart(Long skuId, Integer isChecked);

    //更新购物车商品全部选中状态
    void allCheckCart(Integer isChecked);

    //清空购物车
    void clearCart();

    //获取购物项数据 - 选中的购物车 ---订单界面
    List<CartInfo> getAllCkecked();

    //删除生成订单的购物车商品
    void deleteChecked();
}
