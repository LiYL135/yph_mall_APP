package com.lyl.yph.pay.mapper;

import com.lyl.yph.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentInfoMapper {

    //查询支付信息数据
    void save(PaymentInfo paymentInfo);

    //保存支付信息数据
    PaymentInfo getByOrderNo(String orderNo);

    //更新支付信息
    void updateById(PaymentInfo paymentInfo);
}