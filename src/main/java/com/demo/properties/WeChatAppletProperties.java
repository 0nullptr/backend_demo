package com.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("wxapp")
public class WeChatAppletProperties {
    private String appid;
    private String secret;
    private String grantType;
}
