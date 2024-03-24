package com.lyl.yph.user.service;

/**
 * @Author: lyl
 * @Description: 短信服务接口
 * @Date: 2024/2/3 10:57
 */
public interface SmsService {

    //发送短信验证码
    void sendValidateCode(String phone);
}
