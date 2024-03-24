package com.lyl.yph.manager.service;

import com.lyl.yph.model.dto.system.AssginMenuDto;

import java.util.Map;

/**
 * @Author: lyl
 * @Description: 菜单 角色关系
 * @Date: 2024/1/31 15:49
 */
public interface SysRoleMenuService {

    // 查询角色id的菜单数据
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    // 保存菜单
    void doAssign(AssginMenuDto assginMenuDto);
}
