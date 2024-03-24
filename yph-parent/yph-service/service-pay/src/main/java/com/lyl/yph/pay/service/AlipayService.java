package com.lyl.yph.pay.service;

//阿里支付
public interface AlipayService {

    //支付宝下单
    String submitAlipay(String orderNo);
}
