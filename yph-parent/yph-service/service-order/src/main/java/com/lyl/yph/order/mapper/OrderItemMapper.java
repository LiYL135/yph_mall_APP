package com.lyl.yph.order.mapper;

import com.lyl.yph.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 订单明细
 * @Date: 2024/2/4 15:24
 */
@Mapper
public interface OrderItemMapper {

    //保存订单明细
    void save(OrderItem orderItem);

    //根据订单id获取订单明细
    List<OrderItem> findByOrderId(Long id);
}
