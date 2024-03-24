package com.lyl.yph.feign.user;

import com.lyl.yph.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user") //所在的服务名称
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable Long id);

}