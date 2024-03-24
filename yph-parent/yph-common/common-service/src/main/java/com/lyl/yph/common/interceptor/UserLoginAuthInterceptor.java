package com.lyl.yph.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.lyl.yph.model.entity.user.UserInfo;
import com.lyl.yph.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截前端所有以api开头的接口，把当前用户直接放到ThreadLocal中
 */
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String token = request.getHeader("token");
        String userInfoJSON = redisTemplate.opsForValue().get("user:yph:" + token);
        //放到ThreadLocal中
        AuthContextUtil.setUserInfo(JSON.parseObject(userInfoJSON , UserInfo.class));
        return true ;

    }
}