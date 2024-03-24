package com.lyl.yph.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.yph.common.exception.GuiguException;
import com.lyl.yph.manager.mapper.SysRoleUserMapper;
import com.lyl.yph.manager.mapper.SysUserMapper;
import com.lyl.yph.manager.service.SysUserService;
import com.lyl.yph.model.dto.system.AssginRoleDto;
import com.lyl.yph.model.dto.system.LoginDto;
import com.lyl.yph.model.dto.system.SysUserDto;
import com.lyl.yph.model.entity.system.SysUser;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lyl
 * @Description:
 * @Date: 2024/1/29 16:34
 */

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //用户登录
    @Override
    public LoginVo login(LoginDto loginDto) {

        //获取输入验证码和存储到redis的key名称  loginDto获取到
        String captcha = loginDto.getCaptcha();
        String key = loginDto.getCodeKey();

        //2 根据获取的redis里面key ，查询redis里面存储验证码
        // set("user:validate"+key
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);

        //3 比较输入的验证码和 redis存储验证码是否一致
        if(StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode,captcha)) {
            //4 如果不一致，提示用户，校验失败
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //5 如果一致，删除redis里面验证码
        redisTemplate.delete("user:validate" + key);

        //1 获取提交用户名，loginDto获取到
        String userName = loginDto.getUserName();

        //2 根据用户名查询数据库表 sys_user表
        SysUser sysUser = sysUserMapper.selectByUserName(userName);

        //3 如果根据用户名查不到对应信息，用户不存在，返回错误信息
        if(sysUser == null) {
//            throw new RuntimeException("用户名不存在");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4 如果根据用户名查询到用户信息，用户存在
        //5 获取输入的密码，比较输入的密码和数据库密码是否一致
        String database_password = sysUser.getPassword();
        String input_password =
                DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        //比较
        if(!input_password.equals(database_password)) {
//            throw new RuntimeException("密码不正确");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //6 如果密码一致，登录成功，如果密码不一致登录失败
        //7 登录成功，生成用户唯一标识token
        String token = UUID.randomUUID().toString().replaceAll("-","");

        //8 把登录成功用户信息放到redis里面
        // key : token   value: 用户信息
        redisTemplate.opsForValue()
                .set("user:login"+token,
                        JSON.toJSONString(sysUser),
                        7,
                        TimeUnit.DAYS);

        //9 返回loginvo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    /**
     * 根据token获取用户信息
     */
    public SysUser getUserInfo(String token) {
        //redis中获取
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        return JSON.parseObject(userJson , SysUser.class) ; //转换类型
    }

    /**
     * 用户退出登录
     * @param token
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token) ; //删除token
    }

    /**
     * 分页查询用户信息
     */
    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum , pageSize);
        List<SysUser> sysUserList = sysUserMapper.findByPage(sysUserDto) ;
        PageInfo pageInfo = new PageInfo(sysUserList) ;
        return pageInfo;
    }

    /**
     * 新增用户
     */
    @Override
    public void saveSysUser(SysUser sysUser) {

        // 根据输入的用户名查询用户，先检查用户名是否已存在
        SysUser dbSysUser = sysUserMapper.findByUserName(sysUser.getUserName()) ;
        if(dbSysUser != null) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS) ;
        }

        // 对密码进行加密
        String password = sysUser.getPassword();
        String digestPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(digestPassword);
        sysUser.setStatus(0);
        sysUserMapper.saveSysUser(sysUser) ;
    }

    /**
     * 修改用户
     */
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser) ;
    }

    /**
     * 删除用户
     * @param userId
     */
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId) ;
    }

    /**
     * 用户分配角色，保存分配数据
     * @param assginRoleDto
     */
    @Transactional
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {

        // 删除之前的所有的用户所对应的角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId()) ;

        // 分配新的角色数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList(); //获取分配好的角色id
        roleIdList.forEach(roleId->{
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        });
    }

}
