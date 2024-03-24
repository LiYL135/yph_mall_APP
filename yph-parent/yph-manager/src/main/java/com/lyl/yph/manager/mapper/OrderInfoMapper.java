package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单数据
 */
@Mapper
public interface OrderInfoMapper {

    // 查询指定日期产生的订单数据，统计后的订单数据
    public abstract OrderStatistics selectOrderStatistics(String creatTime);

}