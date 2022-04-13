package com.simple.blog.config;

import com.simple.blog.data.entity.Resource;
import com.simple.blog.data.entity.User;
import com.simple.blog.data.mapper.ResourceMapper;
import com.simple.blog.entity.AdminUserDetails;
import com.simple.blog.security.authorization.DynamicSecurityService;
import com.simple.blog.security.config.SecurityConfig;
import com.simple.blog.service.UserCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 后台应用安全配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends SecurityConfig {

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 获取登录用户信息
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            //  从Redis缓存中获取用户
            User user = userCacheService.getUserCache(username);
            if (null != user) {
                //  从Redis缓存中获取用户的资源列表
                List<Resource> resourceList = userCacheService.getResourceCache(user.getId());
                return new AdminUserDetails(user, resourceList);
            }

            throw new UsernameNotFoundException("用户不存在");
        };
    }

    /**
     * 获取全部资源
     */
    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            ConcurrentHashMap<String, ConfigAttribute> map = new ConcurrentHashMap<>();
            List<Resource> resources = resourceMapper.findAll();
            for (Resource resource : resources) {
                map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
            }
            return map;
        };
    }

}
