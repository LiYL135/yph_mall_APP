package com.lyl.yph.feign.order;

import com.lyl.yph.model.entity.order.OrderInfo;
import com.lyl.yph.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "service-order")
public interface OrderFeignClient {

    //根据orderNo获取订单信息
    @GetMapping("/api/order/orderInfo/auth/getOrderInfoByOrderNo/{orderNo}")
    public Result<OrderInfo> getOrderInfoByOrderNo(@PathVariable String orderNo) ;

    //更新订单状态
    @PostMapping("/api/order/orderInfo/auth/updateOrderStatusPayed/{orderNo}")
    public Result updateOrderStatus(@PathVariable(value = "orderNo") String orderNo );

}