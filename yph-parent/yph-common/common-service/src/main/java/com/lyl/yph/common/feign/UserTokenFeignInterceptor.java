package com.lyl.yph.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 针对于远程调用的拦截器，调用前
 *  使用feign拦截器拦截请求，获取token，重新传递token
 */
public class UserTokenFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();//获取请求
        String token = request.getHeader("token");
        requestTemplate.header("token" , token) ;//传递
    }

}