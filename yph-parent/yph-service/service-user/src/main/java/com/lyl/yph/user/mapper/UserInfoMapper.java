package com.lyl.yph.user.mapper;

import com.lyl.yph.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: lyl
 * @Description:
 * @Date: 2024/2/3 14:02
 */
@Mapper
public interface UserInfoMapper {

    //根据用户名获取用户信息
    UserInfo getByUsername(String username);

    //保存用户信息
    void save(UserInfo userInfo);
}
