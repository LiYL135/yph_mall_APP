package com.lyl.yph.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lyl
 * @Description: 用户与角色关系表操作
 * @Date: 2024/1/31 11:15
 */
@Mapper
public interface SysRoleUserMapper {

    // 删除之前的所有的用户所对应的角色数据
    void deleteByUserId(Long userId); // 根据用户的id删除数据

    // 分配新的角色数据
    void doAssign(@Param("userId") Long userId,
                  @Param("roleId") Long roleId); // 添加关联关系

    //根据用户id查询分配过的角色id
    List<Long> findSysUserRoleByUserId(Long userId);
}
