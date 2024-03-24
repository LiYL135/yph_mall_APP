package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.dto.system.SysRoleDto;
import com.lyl.yph.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    // 分页查询角色信息
    public abstract List<SysRole> findByPage(SysRoleDto sysRoleDto);

    // 添加角色
    void saveSysRole(SysRole sysRole);

    // 修改角色
    void updateSysRole(SysRole sysRole);

    // 删除角色
    void deleteById(Long roleId);

    // 查询所有角色
    List<SysRole> findAllRoles();
}