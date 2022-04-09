package com.simple.blog.security.authorization;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限 Service
 */
public interface DynamicSecurityService {

    /**
     * 获取全部资源，resourceUrl => ConfigAttribute(resourceId:resourceName)
     */
    Map<String, ConfigAttribute> loadDataSource();

}
