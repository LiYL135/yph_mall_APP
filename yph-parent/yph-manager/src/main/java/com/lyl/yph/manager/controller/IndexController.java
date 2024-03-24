package com.lyl.yph.manager.controller;

import com.lyl.yph.manager.service.SysMenuService;
import com.lyl.yph.manager.service.SysUserService;
import com.lyl.yph.manager.service.ValidateCodeService;
import com.lyl.yph.model.dto.system.LoginDto;
import com.lyl.yph.model.entity.system.SysUser;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.model.vo.system.LoginVo;
import com.lyl.yph.model.vo.system.SysMenuVo;
import com.lyl.yph.model.vo.system.ValidateCodeVo;
import com.lyl.yph.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 用户登录和用户首页
 * @Date: 2024/1/29 16:32
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService ;
    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private SysMenuService sysMenuService;

    // 生成图片的验证码
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ;
    }

    // 用户登录验证
    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto) ;
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
    }

    // 获取当前登录用户信息
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo() {
        //可以直接从ThreadLocal中获取信息
        return Result.build(AuthContextUtil.get()  , ResultCodeEnum.SUCCESS) ;
    }

//    @GetMapping(value = "/getUserInfo")
//    public Result getUserInfo(@RequestHeader(name = "token") String token) {
//        //1 从请求头获取token
////        String token = request.getHeader("token");
//        //2 根据token查询redis获取用户信息
//        SysUser sysUser = sysUserService.getUserInfo(token);
//
//        //3 用户信息返回
//        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
//    }

    // 用户退出登录  redis中删除token
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(value = "token") String token) {
        sysUserService.logout(token) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    // 查询用户可以操作的菜单
    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> sysMenuVoList =  sysMenuService.findUserMenuList() ;
        return Result.build(sysMenuVoList , ResultCodeEnum.SUCCESS) ;
    }

}