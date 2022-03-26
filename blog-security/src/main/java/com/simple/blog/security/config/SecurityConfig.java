package com.simple.blog.security.config;

import com.simple.blog.security.component.*;
import com.simple.blog.security.dynamic.DynamicAccessDecisionManager;
import com.simple.blog.security.dynamic.DynamicSecurityFilter;
import com.simple.blog.security.dynamic.DynamicSecurityMetadataSource;
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
 * Spring Security 配置
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置安全过滤器
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();

        //  不需要拦截的 URL 路径
        for (String url : ignoreURLConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }

        //  允许跨域的 OPTIONS 请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();

        //  测试，全部放行
        registry.antMatchers("/**").permitAll();

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
                .accessDeniedHandler(restfulAccessDeniedHandler())    //    处理 AccessDeniedException 异常
                .authenticationEntryPoint(restAuthenticationEntryPoint());  //    处理 AuthenticationException 异常

        //  添加 JWT 登录授权过滤器
        registry.and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //  添加动态权限过滤器
        registry.and()
                .addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
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
    public IgnoreURLConfig ignoreURLConfig() {
        return new IgnoreURLConfig();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
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
