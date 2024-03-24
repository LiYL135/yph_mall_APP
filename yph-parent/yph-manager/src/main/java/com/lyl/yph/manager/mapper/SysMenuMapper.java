package com.lyl.yph.manager.mapper;

import com.lyl.yph.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 菜单管理
 * @Date: 2024/1/31 14:55
 */
@Mapper
public interface SysMenuMapper {

    //查询全部菜单
    List<SysMenu> selectAll();

    //增加菜单
    void insert(SysMenu sysMenu);

    //修改菜单
    void updateById(SysMenu sysMenu);

    //根据id查询子菜单id
    int countByParentId(Long id);

    //根据id删除菜单
    void deleteById(Long id);

    //根据用户id获取菜单列表
    List<SysMenu> selectListByUserId(Long userId);

    // 查询是否存在父节点
    SysMenu selectById(Long parentId);
}
