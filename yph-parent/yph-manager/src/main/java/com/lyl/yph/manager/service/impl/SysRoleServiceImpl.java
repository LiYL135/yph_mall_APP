package com.lyl.yph.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.yph.manager.mapper.SysRoleMapper;
import com.lyl.yph.manager.mapper.SysRoleUserMapper;
import com.lyl.yph.manager.service.SysRoleService;
import com.lyl.yph.model.dto.system.SysRoleDto;
import com.lyl.yph.model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper ;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    // 分页查询角色信息
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {
        // 设置分页参数
        PageHelper.startPage(pageNum , pageSize) ;
        // 根据条件查询所有数据
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto) ; //分页查询
        PageInfo<SysRole> pageInfo = new PageInfo(sysRoleList) ;
        return pageInfo;
    }

    // 添加角色
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole) ;
    }

    // 修改角色
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole) ;
    }

    // 删除角色
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId) ;
    }

    // 查询所有角色
    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        // 1.查询所有角色
        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles() ;

        // 2.根据用户id查询分配过的角色id
        List<Long> sysRoles = sysRoleUserMapper.findSysUserRoleByUserId(userId);

        // 构建响应结果数据
        Map<String , Object> resultMap = new HashMap<>() ;
        resultMap.put("allRolesList" , sysRoleList) ;
        resultMap.put("sysUserRoles", sysRoles);
        return resultMap;
    }

}