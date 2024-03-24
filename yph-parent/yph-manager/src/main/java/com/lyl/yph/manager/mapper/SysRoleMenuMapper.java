package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 菜单 角色关系
 * @Date: 2024/1/31 15:50
 */
@Mapper
public interface SysRoleMenuMapper {

    // 查询角色id的菜单数据
    List<Long> findSysRoleMenuByRoleId(Long roleId);

    // 根据角色的id删除其所对应的菜单数据
    void deleteByRoleId(Long roleId);

    // 分配菜单，保存菜单
    void doAssign(AssginMenuDto assginMenuDto);

    // 将该id的菜单设置为半开
    void updateSysRoleMenuIsHalf(Long id);
}
