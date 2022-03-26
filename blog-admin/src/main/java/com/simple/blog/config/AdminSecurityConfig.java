package com.simple.blog.config;

import com.simple.blog.entity.AdminUserDetails;
import com.simple.blog.entity.Resource;
import com.simple.blog.entity.User;
import com.simple.blog.mapper.ResourceMapper;
import com.simple.blog.mapper.UserMapper;
import com.simple.blog.mapper.UserRoleRelationMapper;
import com.simple.blog.security.dynamic.DynamicSecurityService;
import com.simple.blog.security.config.SecurityConfig;
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
 * 后台管理安全配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends SecurityConfig {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 获取登录用户信息
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            //  todo 缓存用户信息
            //  根据用户名查找用户
            User user = userMapper.selectByUsername(username);
            if (null != user) {
                //  根据用户Id获取用户的资源列表
                List<Resource> resourceList = userRoleRelationMapper.selectResourceList(user.getId());
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
            List<Resource> resources = resourceMapper.selectAll();
            for (Resource resource : resources) {
                map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
            }
            return map;
        };
    }

}
