package com.lyl.yph.manager.service.impl;

//import com.lyl.yph.common.log.annotation.Log;
import com.lyl.yph.manager.mapper.SysRoleMenuMapper;
import com.lyl.yph.manager.service.SysMenuService;
import com.lyl.yph.manager.service.SysRoleMenuService;
import com.lyl.yph.model.dto.system.AssginMenuDto;
import com.lyl.yph.model.entity.system.SysMenu;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysMenuService sysMenuService;
    
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    // 根据角色id查询角色的菜单
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
    
        // 查询所有的菜单数据
        List<SysMenu> sysMenuList = sysMenuService.findNodes() ;
    
        // 查询当前角色的菜单数据
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId) ;
    
        // 将数据存储到Map中进行返回
        Map<String , Object> result = new HashMap<>() ;
        result.put("sysMenuList" , sysMenuList) ;
        result.put("roleMenuIds" , roleMenuIds) ;
    
        // 返回
        return result;
    }

//    @Log(title = "角色菜单模块" , businessType = 2 )
    @Transactional
    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {

        // 根据角色的id删除其所对应的菜单数据
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());

        // 获取菜单的id
        List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
        if(menuInfo != null && menuInfo.size() > 0) {
            sysRoleMenuMapper.doAssign(assginMenuDto) ;
        }

    }

}