package com.simple.blog.security.config;

import com.simple.blog.entity.User;
import com.simple.blog.security.component.AdminUserDetails;
import com.simple.blog.security.component.JwtAuthenticationTokenFilter;
import com.simple.blog.security.component.RestAuthenticationEntryPoint;
import com.simple.blog.security.component.RestfulAccessDeniedHandler;
import com.simple.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private UserService userService;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    /**
     * 配置需要拦截的 URL 路径、JWT 过滤器及出现异常后的处理器
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf() //  由于使用的是 JWT，不需要 csrf
                .disable()
                .sessionManagement()    //  基于 token，不需要 session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                        )   //  允许对于网站静态资源的无授权访问
                .permitAll()
                .antMatchers("/admin/login", "/admin/register") //  对登录注册允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)    //  跨域请求会先进行一次options请求
                .permitAll()
                //.antMatchers("/**")   //  测试时全部可以访问
                //.permitAll()
                .anyRequest()   //  除上面外的所有请求需要鉴权认证
                .authenticated();

        //  禁用缓存
        httpSecurity.headers().cacheControl();

        //  添加 JWT 登录授权过滤器
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //  添加自定义未授权和未登录返回结果
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
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

    /**
     * 获取登录用户信息
     */
    @Bean
    public UserDetailsService userDetailsService() {
        //  根据用户名从数据库中查询用户
        return username -> {
            User user = userService.getUserByUsername(username);
            if (null != user) {
                return new AdminUserDetails(user);
            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
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
