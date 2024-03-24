package com.lyl.yph.user.service.impl;

import com.lyl.yph.model.entity.user.UserAddress;
import com.lyl.yph.user.mapper.UserAddressMapper;
import com.lyl.yph.user.service.UserAddressService;
import com.lyl.yph.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserAddressServiceImpl implements UserAddressService {

   @Autowired
   private UserAddressMapper userAddressMapper;

   //获取用户地址
   @Override
   public List<UserAddress> findUserAddressList() {
      //threadlocal中获取用户id
      Long userId = AuthContextUtil.getUserInfo().getId();
      //根据id获取用户地址信息
      return userAddressMapper.findByUserId(userId);
   }

   //根据收货地址id获取地址信息
   @Override
   public UserAddress getById(Long id) {
      return userAddressMapper.getById(id);
   }

}