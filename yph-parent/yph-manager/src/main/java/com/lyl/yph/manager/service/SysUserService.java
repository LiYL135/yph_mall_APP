package com.lyl.yph.manager.service;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.model.dto.system.AssginRoleDto;
import com.lyl.yph.model.dto.system.LoginDto;
import com.lyl.yph.model.dto.system.SysUserDto;
import com.lyl.yph.model.entity.system.SysUser;
import com.lyl.yph.model.vo.system.LoginVo;

/**
 * @Author: lyl
 * @Description:
 * @Date: 2024/1/29 16:33
 */
public interface SysUserService {

    // 用户登录
    LoginVo login(LoginDto loginDto);

    // 根据token获取用户信息
    SysUser getUserInfo(String token);

    // 用户退出登录
    void logout(String token);

    // 分页查询用户信息
    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    // 新增用户
    void saveSysUser(SysUser sysUser);

    // 修改用户
    void updateSysUser(SysUser sysUser);

    // 删除用户
    void deleteById(Long userId);

    // 用户分配角色，保存分配数据
    void doAssign(AssginRoleDto assginRoleDto);
}
