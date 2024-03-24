package com.lyl.yph.user.controller;

import com.lyl.yph.model.dto.h5.UserLoginDto;
import com.lyl.yph.model.dto.h5.UserRegisterDto;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.model.vo.h5.UserInfoVo;
import com.lyl.yph.user.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lyl
 * @Description: 客户端 - 用户
 * @Date: 2024/2/3 13:42
 */
@RestController
@RequestMapping("/api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "注册")
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto) {
        userInfoService.register(userRegisterDto);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "登录")
    @PostMapping("login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        return Result.build(userInfoService.login(userLoginDto), ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("auth/getCurrentUserInfo")
    public Result<UserInfoVo> getCurrentUserInfo(HttpServletRequest request) {

        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token) ;
        return Result.build(userInfoVo , ResultCodeEnum.SUCCESS) ;

    }

}
