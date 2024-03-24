package com.lyl.yph.order.service;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.model.dto.h5.OrderInfoDto;
import com.lyl.yph.model.entity.order.OrderInfo;
import com.lyl.yph.model.vo.h5.TradeVo;

/**
 * 订单管理
 */
public interface OrderInfoService {

    //确认下单
    TradeVo getTrade();

    //提交订单
    Long submitOrder(OrderInfoDto orderInfoDto);

    //根据订单id，获取订单信息
    OrderInfo getOrderInfo(Long orderId);

    //立即购买
    TradeVo buy(Long skuId);

    //获取订单分页列表
    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus);

    //根据orderNo 获取订单信息
    OrderInfo getByOrderNo(String orderNo);

    //更新订单状态
    void updateOrderStatus(String orderNo);

}