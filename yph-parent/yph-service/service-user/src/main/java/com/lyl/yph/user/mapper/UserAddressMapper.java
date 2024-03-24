package com.lyl.yph.user.mapper;

import com.lyl.yph.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户地址
 */
@Mapper
public interface UserAddressMapper {

    //根据userId获取地址信息
    List<UserAddress> findByUserId(Long userId);

    //获取地址信息
    UserAddress getById(Long id);
}