package com.lyl.yph.common.anno;

import com.lyl.yph.common.config.UserWebMvcConfiguration;
import com.lyl.yph.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义注解 -- 使可以使用 用户验证的拦截器
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = { UserLoginAuthInterceptor.class , UserWebMvcConfiguration.class})
public @interface EnableUserLoginAuthInterceptor {

}