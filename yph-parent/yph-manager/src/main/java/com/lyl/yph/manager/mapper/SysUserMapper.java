package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.dto.system.SysUserDto;
import com.lyl.yph.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description:
 * @Date: 2024/1/29 16:35
 */
@Mapper
public interface SysUserMapper {


    //根据用户名查询用户数据
    SysUser selectByUserName(String userName);

    //分页查询用户信息
    List<SysUser> findByPage(SysUserDto sysUserDto);

    // 根据输入的用户名查询用户
    SysUser findByUserName(String userName);

    // 保存用户
    void saveSysUser(SysUser sysUser);

    // 修改用户
    void updateSysUser(SysUser sysUser);

    // 删除用户
    void deleteById(Long userId);
}
