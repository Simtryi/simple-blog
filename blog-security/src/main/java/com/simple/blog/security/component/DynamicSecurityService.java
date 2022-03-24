package com.simple.blog.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务类
 */
public interface DynamicSecurityService {

    /**
     * 加载资源 Ant 通配符和资源对应 Map
     */
    Map<String, ConfigAttribute> loadDataSource();

}
