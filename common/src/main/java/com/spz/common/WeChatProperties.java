package com.spz.common;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spz.wechat")
@Data
public class WeChatProperties {
    private String appid;
    private String secret;
}
