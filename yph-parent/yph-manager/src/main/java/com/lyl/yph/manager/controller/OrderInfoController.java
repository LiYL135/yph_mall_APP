package com.lyl.yph.manager.controller;

import com.lyl.yph.manager.service.OrderInfoService;
import com.lyl.yph.model.dto.order.OrderStatisticsDto;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单数据 统计
 */
@RestController
@RequestMapping(value="/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService ;

    //获取更详细的订单统计数据
    @GetMapping("/getOrderStatisticsData")
    public Result<OrderStatisticsVo> getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        OrderStatisticsVo orderStatisticsVo = orderInfoService.getOrderStatisticsData(orderStatisticsDto) ;
        return Result.build(orderStatisticsVo , ResultCodeEnum.SUCCESS) ;
    }

}