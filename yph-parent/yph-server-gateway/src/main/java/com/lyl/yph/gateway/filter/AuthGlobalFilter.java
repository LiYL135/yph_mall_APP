package com.lyl.yph.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lyl.yph.model.entity.user.UserInfo;
import com.lyl.yph.model.vo.common.Result;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 全局Filter，统一处理登录校验
 *
 */

//@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    //路径的匹配
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求路径
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
//        log.info("path {}", path);

        UserInfo userInfo = this.getUserInfo(request);
        //api接口，异步请求，校验用户必须登录   //判断路径 ，登录校验
        if(antPathMatcher.match("/api/**/auth/**", path)) {
            //登录校验
            if(null == userInfo) { //返回错误信息
                ServerHttpResponse response = exchange.getResponse();
                return out(response, ResultCodeEnum.LOGIN_AUTH);
            }
        }

        //放行
        return chain.filter(exchange);
    }

    //优先级
    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        // 指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    /**
     * 获取userInfo
     * @param request
     * @return
     */
    private UserInfo getUserInfo(ServerHttpRequest request) {
        String token = "";
        // 从请求头获取token
        List<String> tokenList = request.getHeaders().get("token");
        if(null  != tokenList) {
            token = tokenList.get(0);
        }
        // 判断是否为空
        if(!StringUtils.isEmpty(token)) {
            //根据token去查找redis
            String userInfoJSON = redisTemplate.opsForValue().get("user:yph:"+token);
            if(StringUtils.isEmpty(userInfoJSON)) {
                return null ;
            }else {
                //返回userInfo
                return JSON.parseObject(userInfoJSON , UserInfo.class) ;
            }
        }
        return null;
    }
}