package com.lyl.yph.manager.controller;

import com.github.pagehelper.PageInfo;
import com.lyl.yph.manager.service.SysUserService;
import com.lyl.yph.model.dto.system.AssginRoleDto;
import com.lyl.yph.model.dto.system.SysUserDto;
import com.lyl.yph.model.entity.system.SysRole;
import com.lyl.yph.model.entity.system.SysUser;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService ;

    /**
     * 分页查询用户信息
     * @param sysUserDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(SysUserDto sysUserDto ,
                                                @PathVariable(value = "pageNum") Integer pageNum ,
                                                @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto , pageNum , pageSize) ;
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    @PostMapping(value = "/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 修改用户
     * @param sysUser
     * @return
     */
    @PutMapping(value = "/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/deleteById/{userId}")
    public Result deleteById(@PathVariable(value = "userId") Long userId) {
        sysUserService.deleteById(userId) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    /**
     * 用户分配角色，保存分配数据
     * @param assginRoleDto
     * @return
     */
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
        sysUserService.doAssign(assginRoleDto) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}