package com.lyl.yph.manager.service;

import com.lyl.yph.model.entity.system.SysMenu;
import com.lyl.yph.model.vo.system.SysMenuVo;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 菜单管理
 * @Date: 2024/1/31 14:53
 */
public interface SysMenuService {

    // 获取菜单列表
    List<SysMenu> findNodes();

    // 添加菜单
    void save(SysMenu sysMenu);

    // 修改菜单
    void updateById(SysMenu sysMenu);

    // 删除菜单
    void removeById(Long id);

    // 查询用户可以操作的菜单
    List<SysMenuVo> findUserMenuList();
}
