package com.simple.blog.security.config;

import com.simple.blog.security.component.JwtAuthenticationTokenFilter;
import com.simple.blog.security.component.RestAuthenticationEntryPoint;
import com.simple.blog.security.component.RestfulAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置需要拦截的 URL 路径、JWT 过滤器及出现异常后的处理器
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();

        //  不需要拦截的 URL 路径
        for (String url : ignoreURLConfig().getUrls()) {
            registry.antMatchers(url).permitAll();
        }

        //  允许跨域请求的 OPTIONS 请求
        registry.antMatchers(HttpMethod.OPTIONS).permitAll();

        //  任何其他请求都需要身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                .and()
                .csrf() //  关闭跨站请求防护
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //  不使用 session

                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())    //  自定义未授权返回结果
                .authenticationEntryPoint(restAuthenticationEntryPoint()) //  自定义未登录返回结果

                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);   //  自定义 JWT 登录授权过滤器
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

    /**
     * 采用 bcrypt 密码编码方式
     */
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
