package com.lyl.yph.manager.helper;

import com.lyl.yph.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建树形菜单的工具类
 */

public class MenuHelper {

    /**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        //创建list集合，用于封装最终的数据
        List<SysMenu> trees = new ArrayList<>();
        //遍历所有菜单集合
        for (SysMenu sysMenu : sysMenuList) {
            //找到第一层的菜单
            if (sysMenu.getParentId().longValue() == 0) {
                //根据第一层，找下一层数据，使用递归完成
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu it : treeNodes) {
            if(sysMenu.getId().longValue() == it.getParentId().longValue()) {
                //if (sysMenu.getChildren() == null) {
                //    sysMenu.setChildren(new ArrayList<>());
                //}
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;
    }
}