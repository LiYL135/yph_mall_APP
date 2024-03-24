package com.lyl.yph.manager;

import com.lyl.yph.common.log.annotation.EnableLogAspect;
import com.lyl.yph.manager.properties.MinioProperties;
import com.lyl.yph.manager.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: lyl
 * @Description: 启动类
 * @Date: 2024/1/29 16:30
 */

@SpringBootApplication
@ComponentScan(basePackages = {"com.lyl.yph"}) //使之能够扫描到common-service项目中的Knife4jConfig
@EnableConfigurationProperties(value = {UserAuthProperties.class, MinioProperties.class})
@EnableScheduling //开启定时任务功能
@EnableLogAspect // AOP
@EnableAsync //通过异步线程执行saveSysOperLog方法
public class ManagerApplication {

    public static void main(String[] args) {

        SpringApplication.run(ManagerApplication.class,args);

    }
}
