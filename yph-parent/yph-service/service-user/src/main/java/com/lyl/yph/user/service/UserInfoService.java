package com.lyl.yph.user.service;

import com.lyl.yph.model.dto.h5.UserLoginDto;
import com.lyl.yph.model.dto.h5.UserRegisterDto;
import com.lyl.yph.model.vo.h5.UserInfoVo;

/**
 * @Author: lyl
 * @Description: 客户端-用户
 * @Date: 2024/2/3 13:46
 */
public interface UserInfoService {

    //用户登录
    Object login(UserLoginDto userLoginDto);

    //获取当前用户信息
    UserInfoVo getCurrentUserInfo(String token);

    //注册
    void register(UserRegisterDto userRegisterDto);
}
