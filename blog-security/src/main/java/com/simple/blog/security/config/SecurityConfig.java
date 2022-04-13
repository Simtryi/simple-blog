package com.simple.blog.security.config;

import com.simple.blog.security.properties.SecurityProperties;
import com.simple.blog.security.authentication.JwtAuthenticationFilter;
import com.simple.blog.security.exception.CustomAuthenticationEntryPoint;
import com.simple.blog.security.authorization.DynamicAccessDecisionManager;
import com.simple.blog.security.authorization.DynamicSecurityInterceptor;
import com.simple.blog.security.authorization.DynamicSecurityMetadataSource;
import com.simple.blog.security.exception.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 配置安全过滤器
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();

        //  跨域的 OPTIONS 请求，直接放行
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();

        //  白名单请求，直接放行
        for (String url : securityProperties.getWhiteList()) {
            registry.antMatchers(url).permitAll();
        }

        ////  测试，全部放行
        //registry.antMatchers("/**").permitAll();

        //  其他任何请求都需要身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                .and()
                .csrf() //  由于采用 token，不需要跨站请求防护
                .disable()
                .sessionManagement()    //  由于采用 token，不使用 session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()    //  自定义异常处理
                .authenticationEntryPoint(customAuthenticationEntryPoint())   //    处理认证异常
                .accessDeniedHandler(customAccessDeniedHandler()); //    处理授权异常

        //  添加 JWT 认证过滤器
        registry.and()
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        //  添加动态权限授权拦截器
        registry.and()
                .addFilterBefore(dynamicSecurityInterceptor(), FilterSecurityInterceptor.class);
    }

    /**
     * 配置认证管理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //  配置 UserDetailsService 及 PasswordEncoder
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public DynamicSecurityInterceptor dynamicSecurityInterceptor() {
        return new DynamicSecurityInterceptor();
    }

    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }

    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
