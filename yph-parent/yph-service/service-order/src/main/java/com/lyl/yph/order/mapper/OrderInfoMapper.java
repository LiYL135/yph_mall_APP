package com.lyl.yph.order.mapper;

import com.lyl.yph.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 订单信息
 * @Date: 2024/2/4 15:23
 */
@Mapper
public interface OrderInfoMapper {

    //保存订单信息
    void save(OrderInfo orderInfo);

    //根据订单id，获取订单信息
    OrderInfo getById(Long orderId);

    //根据 用户id、订单状态，分页获取订单信息列表
    List<OrderInfo> findUserPage(Long userId, Integer orderStatus);

    //根据orderNo查询订单信息
    OrderInfo getByOrderNo(String orderNo);

    //更新订单状态
    void updateById(OrderInfo orderInfo);
}
