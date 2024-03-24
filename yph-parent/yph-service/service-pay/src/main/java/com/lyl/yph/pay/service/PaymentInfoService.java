package com.lyl.yph.pay.service;

import com.lyl.yph.model.entity.pay.PaymentInfo;

import java.util.Map;

/**
 * 支付信息
 */
public interface PaymentInfoService {

    //保存支付信息
    PaymentInfo savePaymentInfo(String orderNo);

    //支付成功，更新交易记录状态
    void updatePaymentStatus(Map<String, String> map);
}