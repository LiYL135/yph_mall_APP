package com.lyl.yph.user.service.impl;

import com.lyl.yph.common.exception.GuiguException;
import com.lyl.yph.model.vo.common.ResultCodeEnum;
import com.lyl.yph.user.service.SmsService;
import com.lyl.yph.utils.HttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    /**
     * 发送短信验证码
     */
    @Override
    public void sendValidateCode(String phone) {
        // 先判断redis中是否有该验证码信息   在redis中搞了个固定值，后面就不多次使用短信服务了
        String code = redisTemplate.opsForValue().get("phone:code:" + phone);
        if(StringUtils.hasText(code)) {
            return;
        }

        // 生成验证码
        String validateCode = RandomStringUtils.randomNumeric(4);
        // 过期时间 5分钟
        redisTemplate.opsForValue().set("phone:code:" + phone , validateCode , 5 , TimeUnit.MINUTES);
        sendSms(phone , validateCode) ;
    } 

    // 发送短信方法
    public void sendSms(String phone, String validateCode) {

        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "ccdb5950110f41bab371444129f1b114";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+validateCode);
        bodys.put("template_id", "CST_ptdie100");
        bodys.put("phone_number", phone);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}