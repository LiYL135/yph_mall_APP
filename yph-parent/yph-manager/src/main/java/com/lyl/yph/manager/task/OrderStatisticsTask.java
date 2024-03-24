package com.lyl.yph.manager.task;

import cn.hutool.core.date.DateUtil;
import com.lyl.yph.manager.mapper.OrderInfoMapper;
import com.lyl.yph.manager.mapper.OrderStatisticsMapper;
import com.lyl.yph.model.entity.order.OrderStatistics;
import com.lyl.yph.model.entity.order.OrderStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务，进行订单数据统计
 */
@Component
@Slf4j
public class OrderStatisticsTask {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    // cron表达式
    @Scheduled(cron = "0 0 2 * * ?")
    public void orderTotalAmountStatistics() {
        //创建时间
        String createTime = DateUtil.offsetDay(new Date(), -1)
                .toString(new SimpleDateFormat("yyyy-MM-dd"));
        //查询订单数据的统计结果
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
        if(orderStatistics != null) {
            //将单数据的统计结果 存入对应数据库
            orderStatisticsMapper.insert(orderStatistics) ;
        }
    }

}