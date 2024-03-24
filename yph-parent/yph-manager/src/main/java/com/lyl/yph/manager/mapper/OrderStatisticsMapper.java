package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.dto.order.OrderStatisticsDto;
import com.lyl.yph.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单统计数据
 */
@Mapper
public interface OrderStatisticsMapper {

    //将统计数据存入数据库
    public abstract void insert(OrderStatistics orderStatistics);

    // 查询统计结果数据
    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}