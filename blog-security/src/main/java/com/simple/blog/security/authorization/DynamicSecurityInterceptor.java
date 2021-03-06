package com.simple.blog.security.authorization;

import com.simple.blog.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 动态权限授权拦截器
 */
public class DynamicSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation invocation = new FilterInvocation(servletRequest, servletResponse, filterChain);

        //  跨域的 OPTIONS 请求，直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
            return;
        }

        //  白名单请求，直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String url : securityProperties.getWhiteList()) {
            if (pathMatcher.match(url, request.getRequestURI())) {
                invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
                return;
            }
        }

        //  调用 AccessDecisionManager 中的 decide 方法判断用户是否有访问权限
        InterceptorStatusToken token = super.beforeInvocation(invocation);
        try {
            invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }

}
