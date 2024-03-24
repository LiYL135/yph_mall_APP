package com.lyl.yph.order;

import com.lyl.yph.common.anno.EnableUserLoginAuthInterceptor;
import com.lyl.yph.common.anno.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.lyl.yph.feign.cart" , "com.lyl.yph.feign.product", "com.lyl.yph.feign.user"
})
@EnableUserTokenFeignInterceptor
@EnableUserLoginAuthInterceptor
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class , args) ;
    }

}