package com.lyl.yph.cart;

import com.lyl.yph.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  // 排除数据库的自动化配置，Cart微服务不需要访问数据库
@EnableFeignClients(basePackages = {"com.lyl.yph.feign.product"})  //远程调用
@EnableUserLoginAuthInterceptor // ThreadLocal 里面的数据
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class , args) ;
    }

}