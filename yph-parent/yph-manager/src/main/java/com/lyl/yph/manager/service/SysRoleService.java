package com.lyl.yph.manager.service;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.model.dto.system.SysRoleDto;
import com.lyl.yph.model.entity.system.SysRole;

import java.util.Map;

public interface SysRoleService {
    //分页查询角色信息
    public abstract PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    //添加角色
    void saveSysRole(SysRole sysRole);

    //修改角色
    void updateSysRole(SysRole sysRole);

    //删除角色
    void deleteById(Long roleId);

    //查询所有角色
    Map<String, Object> findAllRoles(Long userId);
}