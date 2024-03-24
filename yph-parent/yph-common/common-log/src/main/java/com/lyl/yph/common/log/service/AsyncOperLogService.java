package com.lyl.yph.common.log.service;

import com.lyl.yph.model.entity.system.SysOperLog;

public interface AsyncOperLogService {

    // 保存日志数据
    public abstract void saveSysOperLog(SysOperLog sysOperLog) ;
}