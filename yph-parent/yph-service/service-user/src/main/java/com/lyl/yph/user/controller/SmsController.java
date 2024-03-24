package com.lyl.yph.user.controller;

import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送短信接口开发
 */
@RestController
@RequestMapping("api/user/sms")
public class SmsController {

    @Autowired
    private SmsService smsService ;

    @GetMapping(value = "/sendCode/{phone}")
    public Result sendValidateCode(@PathVariable String phone) {

        smsService.sendValidateCode(phone);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}