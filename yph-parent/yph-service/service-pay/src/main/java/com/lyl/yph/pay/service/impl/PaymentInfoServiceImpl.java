package com.lyl.yph.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.lyl.yph.feign.order.OrderFeignClient;
import com.lyl.yph.feign.product.ProductFeignClient;
import com.lyl.yph.model.dto.product.SkuSaleDto;
import com.lyl.yph.model.entity.order.OrderInfo;
import com.lyl.yph.model.entity.order.OrderItem;
import com.lyl.yph.model.entity.pay.PaymentInfo;
import com.lyl.yph.pay.mapper.PaymentInfoMapper;
import com.lyl.yph.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 支付信息
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper ;

    @Autowired
    private OrderFeignClient orderFeignClient ;

    @Autowired
    private ProductFeignClient productFeignClient;

    //保存支付信息  orderNo订单号
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {

        // 查询支付信息数据，如果已经已经存在了就不用进行保存(一个订单支付失败以后可以继续支付)
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(orderNo);
        if(null == paymentInfo) {
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo).getData();
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentInfoMapper.save(paymentInfo);
        }
        return paymentInfo;
    }

    // 支付完成后的更新订单状态
    @Transactional
    @Override
    public void updatePaymentStatus(Map<String, String> map) {

        // 1、查询PaymentInfo
        // 2、更新支付信息
        // 3、更新订单的支付状态

        // 查询PaymentInfo  out_trade_no 支付编号
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(map.get("out_trade_no"));
        if (paymentInfo.getPaymentStatus() == 1) { //1 已支付 ，直接返回
            return;
        }

        //没有更新 状态不为1，更新支付信息
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(map.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(map));
        paymentInfoMapper.updateById(paymentInfo);

        //更新订单状态
        orderFeignClient.updateOrderStatus(paymentInfo.getOrderNo());

        //更新sku商品销量、数量
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(paymentInfo.getOrderNo()).getData(); //
        List<SkuSaleDto> skuSaleDtoList = orderInfo.getOrderItemList().stream().map(item -> {
            SkuSaleDto skuSaleDto = new SkuSaleDto();
            skuSaleDto.setSkuId(item.getSkuId());
            skuSaleDto.setNum(item.getSkuNum());
            return skuSaleDto;
        }).collect(Collectors.toList());
        productFeignClient.updateSkuSaleNum(skuSaleDtoList) ;

    }

}