package com.lyl.yph.manager.service;

import com.lyl.yph.model.vo.system.ValidateCodeVo;

/**
 * 图片验证码
 */
public interface ValidateCodeService {

    // 获取验证码图片
    public abstract ValidateCodeVo generateValidateCode();

}