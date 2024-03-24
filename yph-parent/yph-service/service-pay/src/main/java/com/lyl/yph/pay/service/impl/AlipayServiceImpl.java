package com.lyl.yph.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.lyl.yph.common.exception.GuiguException;
import com.lyl.yph.model.entity.pay.PaymentInfo;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.pay.properties.AlipayProperties;
import com.lyl.yph.pay.service.AlipayService;
import com.lyl.yph.pay.service.PaymentInfoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private AlipayProperties alipayProperties ;

    //阿里支付
    @SneakyThrows  // lombok的注解，对外声明异常 ？？？
    @Override
    public String submitAlipay(String orderNo) {

        //保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);

        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        // 同步回调
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());

        // 异步回调
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no",paymentInfo.getOrderNo());
        map.put("product_code","QUICK_WAP_WAY");
        //map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount",new BigDecimal("0.01"));
        map.put("subject",paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        // 发送请求
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
        if(response.isSuccess()){
            log.info("调用成功");
            return response.getBody();
        } else {
            log.info("调用失败");
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }

}