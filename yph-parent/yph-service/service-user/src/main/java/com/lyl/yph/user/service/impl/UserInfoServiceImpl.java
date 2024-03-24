package com.lyl.yph.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.lyl.yph.common.exception.GuiguException;
import com.lyl.yph.model.dto.h5.UserLoginDto;
import com.lyl.yph.model.dto.h5.UserRegisterDto;
import com.lyl.yph.model.entity.user.UserInfo;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.model.vo.h5.UserInfoVo;
import com.lyl.yph.user.mapper.UserInfoMapper;
import com.lyl.yph.user.service.UserInfoService;
import com.lyl.yph.utils.AuthContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 注册功能
     * @param userRegisterDto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserRegisterDto userRegisterDto) {

        // userRegisterDto获取数据
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();

        //校验参数
        if(StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickName) ||
                StringUtils.isEmpty(code)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        //校验验证码
        // redis中获取发送的验证码
        String codeValueRedis = redisTemplate.opsForValue().get("phone:code:" + username); //这里的username就是电话号码
        if(!code.equals(codeValueRedis)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //根据用户名获取用户信息
        UserInfo userInfo = userInfoMapper.getByUsername(username);
        if(null != userInfo) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //保存用户信息
        userInfo = new UserInfo(); //防止空指针异常
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1); //状态
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);

        // 删除Redis中的数据
        redisTemplate.delete("phone:code:" + username) ;
    }

    /**
     * 用户登录
     * @param userLoginDto
     * @return
     */
    @Override
    public String login(UserLoginDto userLoginDto) {

        String username = userLoginDto.getUsername(); //输入的用户名
        String password = userLoginDto.getPassword(); //输入的密码

        //校验参数
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        // 从数据库通过 用户名 获取 用户信息
        UserInfo userInfo = userInfoMapper.getByUsername(username);
        if(null == userInfo) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //校验密码
        String md5InputPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!md5InputPassword.equals(userInfo.getPassword())) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }

        //保存token+userinfo 到redis，30天过期
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:yph:" + token, JSON.toJSONString(userInfo),
                30, TimeUnit.DAYS); //30天
        return token;
    }

    /**
     * 获取当前用户信息
     * @param token
     * @return
     */
    @Override
    public UserInfoVo getCurrentUserInfo(String token) {

        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo ;
    }

}
