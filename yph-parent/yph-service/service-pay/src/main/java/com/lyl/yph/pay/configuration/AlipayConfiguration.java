package com.lyl.yph.pay.configuration;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.lyl.yph.pay.properties.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//配置类，配置发送请求的核心对象：AlipayClient

@Configuration
public class AlipayConfiguration {

    @Autowired
    private AlipayProperties alipayProperties ;

    @Bean
    public AlipayClient alipayClient(){
        AlipayClient alipayClient = new DefaultAlipayClient(alipayProperties.getAlipayUrl() ,
                alipayProperties.getAppId() ,
                alipayProperties.getAppPrivateKey() ,
                AlipayProperties.format ,
                AlipayProperties.charset ,
                alipayProperties.getAlipayPublicKey() ,
                AlipayProperties.sign_type );
        return alipayClient;
    }

}