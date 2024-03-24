package com.lyl.yph.manager.controller;

import com.lyl.yph.manager.service.SysMenuService;
import com.lyl.yph.model.entity.system.SysMenu;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 */

@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {
   
   @Autowired
   private SysMenuService sysMenuService;

   // 菜单列表
   @GetMapping("/findNodes")
   public Result<List<SysMenu>> findNodes() {
      List<SysMenu> list = sysMenuService.findNodes();
      return Result.build(list , ResultCodeEnum.SUCCESS) ;
   }

   // 添加菜单
   @PostMapping("/save")
   public Result save(@RequestBody SysMenu sysMenu) {
      sysMenuService.save(sysMenu);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }

   // 修改菜单
   @PutMapping("/updateById")
   public Result updateById(@RequestBody SysMenu sysMenu) {
      sysMenuService.updateById(sysMenu);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }

   // 删除菜单
   @DeleteMapping("/removeById/{id}")
   public Result removeById(@PathVariable Long id) {
      sysMenuService.removeById(id);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }

}