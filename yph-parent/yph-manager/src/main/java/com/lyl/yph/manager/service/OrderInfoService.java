package com.lyl.yph.manager.service;

import com.lyl.yph.model.dto.order.OrderStatisticsDto;
import com.lyl.yph.model.vo.order.OrderStatisticsVo;

/**
 * @Author: lyl
 * @Description: 订单数据
 * @Date: 2024/2/1 20:55
 */
public interface OrderInfoService {

    //获取订单统计数据
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
