package com.lyl.yph.manager.service.impl;

import com.lyl.yph.common.log.service.AsyncOperLogService;
import com.lyl.yph.manager.mapper.SysOperLogMapper;
import com.lyl.yph.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 日志保存
 */
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

//    @Async      // 异步执行保存日志操作
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
    
}