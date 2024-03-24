package com.lyl.yph.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "yph.auth")      // 前缀不能使用驼峰命名
public class UserAuthProperties {
    private List<String> noAuthUrls ;
}
