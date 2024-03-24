package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: lyl
 * @Description: 日志数据
 * @Date: 2024/2/2 10:53
 */
@Mapper
public interface SysOperLogMapper {

    // 保存日志数据
    void insert(SysOperLog sysOperLog);
}
