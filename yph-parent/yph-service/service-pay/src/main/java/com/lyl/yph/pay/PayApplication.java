package com.lyl.yph.pay;

import com.lyl.yph.pay.properties.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.lyl.yph.feign.order", "com.lyl.yph.feign.product"
})
@EnableConfigurationProperties(value = { AlipayProperties.class })
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class , args) ;
    }

}