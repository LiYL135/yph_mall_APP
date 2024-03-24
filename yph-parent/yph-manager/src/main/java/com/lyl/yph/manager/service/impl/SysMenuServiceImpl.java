package com.lyl.yph.manager.service.impl;

import com.lyl.yph.common.exception.GuiguException;
import com.lyl.yph.manager.helper.MenuHelper;
import com.lyl.yph.manager.mapper.SysMenuMapper;
import com.lyl.yph.manager.mapper.SysRoleMenuMapper;
import com.lyl.yph.manager.service.SysMenuService;
import com.lyl.yph.model.entity.system.SysMenu;
import com.lyl.yph.model.entity.system.SysUser;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.model.vo.system.SysMenuVo;
import com.lyl.yph.utils.AuthContextUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * 菜单管理
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper ;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    // 查询所有菜单
    @Override
    public List<SysMenu> findNodes() {
        // 查询所有菜单，返回一个集合
        List<SysMenu> sysMenuList = sysMenuMapper.selectAll() ;
        // 判断集合是否为空
        if (CollectionUtils.isEmpty(sysMenuList)){
            return null;
        }
        // 使用MenuHelper工具类，返回树形菜单
        List<SysMenu> treeList = MenuHelper.buildTree(sysMenuList); //构建树形数据
        return treeList;
    }

    // 添加菜单
//    @Override
//    public void save(SysMenu sysMenu) {
//        sysMenuMapper.insert(sysMenu) ;
//    }

    @Transactional
    @Override
    public void save(SysMenu sysMenu) {

        // 添加新的节点
        sysMenuMapper.insert(sysMenu) ;

        // 新添加一个菜单，就将该菜单所对应的父级菜单设置为半开
        updateSysRoleMenuIsHalf(sysMenu) ;
    }

    /**
     * 方法：将该菜单所对应的父级菜单设置为半开
     * @param sysMenu
     */
    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {

        // 查询是否存在父节点
        SysMenu parentMenu = sysMenuMapper.selectById(sysMenu.getParentId());

        if(parentMenu != null) {

            // 将该id的菜单设置为半开
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId()) ;

            // 递归调用
            updateSysRoleMenuIsHalf(parentMenu) ;

        }

    }

    // 修改菜单
    @Override
    public void updateById(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu) ;
    }

    //删除菜单
    @Override
    public void removeById(Long id) {
        int count = sysMenuMapper.countByParentId(id);  // 先查询是否存在子菜单，如果存在不允许进行删除
        if (count > 0) {
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.deleteById(id);		// 不存在子菜单直接删除
    }

    // 查询用户可以操作的菜单
    @Override
    public List<SysMenuVo> findUserMenuList() {

        // 获取当前登录用户的id
        SysUser sysUser = AuthContextUtil.get(); //从threadLocal中获取用户信息
        Long userId = sysUser.getId();

        //根据userId查询可以操作的菜单
        List<SysMenu> sysMenuList = sysMenuMapper.selectListByUserId(userId) ;

        //构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        return this.buildMenus(sysMenuTreeList);
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }

}