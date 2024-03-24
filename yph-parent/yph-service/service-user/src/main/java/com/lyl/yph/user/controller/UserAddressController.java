package com.lyl.yph.user.controller;

import com.lyl.yph.model.entity.user.UserAddress;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "用户地址接口")
@RestController
@RequestMapping(value="/api/user/userAddress")
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserAddressController {
   
   @Autowired
   private UserAddressService userAddressService;

   @Operation(summary = "获取用户地址列表")
   @GetMapping("auth/findUserAddressList")
   public Result<List<UserAddress>> findUserAddressList() {
      List<UserAddress> list = userAddressService.findUserAddressList();
      return Result.build(list , ResultCodeEnum.SUCCESS) ;
   }

   @Operation(summary = "根据收货地址id获取收货地址信息")
   @GetMapping("getUserAddress/{id}")
   public UserAddress getUserAddress(@PathVariable Long id) {
      return userAddressService.getById(id);
   }

}