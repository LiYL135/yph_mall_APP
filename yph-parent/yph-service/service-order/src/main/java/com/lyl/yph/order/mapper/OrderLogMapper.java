package com.lyl.yph.order.mapper;

import com.lyl.yph.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: lyl
 * @Description: 订单日志
 * @Date: 2024/2/4 15:24
 */
@Mapper
public interface OrderLogMapper {

    //记录日志
    void save(OrderLog orderLog);
}
