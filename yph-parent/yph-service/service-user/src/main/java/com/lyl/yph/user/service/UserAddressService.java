package com.lyl.yph.user.service;

import com.lyl.yph.model.entity.user.UserAddress;

import java.util.List;

/**
 * 用户地址
 */
public interface UserAddressService {

    //获取用户地址
    List<UserAddress> findUserAddressList();

    //获取地址信息
    UserAddress getById(Long id);
}