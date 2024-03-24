package com.lyl.yph.manager.controller;

import com.github.pagehelper.PageInfo;
//import com.lyl.yph.common.log.annotation.Log;
import com.lyl.yph.manager.service.SysRoleService;
import com.lyl.yph.model.dto.system.SysRoleDto;
import com.lyl.yph.model.entity.system.SysRole;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 角色管理
 */
@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService ;

    /**
     * 根据角色名进行分页查询
     * @param sysRoleDto 条件角色名称
     * @param current 当前页
     * @param limit 每页的记录数
     * @return
     */
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto) {
        //pageHelper插件实现分页
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto,current,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加角色
     * @param SysRole
     * @return
     */
//    @Log(title = "角色添加",businessType = 0) //添加Log注解，设置属性
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole SysRole) {
        sysRoleService.saveSysRole(SysRole) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 修改角色
     */
    @PutMapping(value = "/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 删除角色
     */
    @DeleteMapping(value = "/deleteById/{roleId}")
    public Result deleteById(@PathVariable(value = "roleId") Long roleId) {
        sysRoleService.deleteById(roleId) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 查询所有角色，用户分配角色时使用
     * @return
     */
    @GetMapping(value = "/findAllRoles/{userId}")
    public Result<Map<String , Object>> findAllRoles(@PathVariable(value = "userId") Long userId) {
        Map<String, Object> resultMap = sysRoleService.findAllRoles(userId); //改成Map，一个所有角色，一个分配过的角色
        return Result.build(resultMap , ResultCodeEnum.SUCCESS)  ;
    }

}