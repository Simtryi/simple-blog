package com.simple.blog.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security 相关配置
 */
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    /**
     * Spring Security 白名单
     */
    private List<String> whiteList = new ArrayList<>();



    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }

}
