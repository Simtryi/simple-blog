package com.simple.blog.security.component;

import com.simple.blog.common.constants.Constants;
import com.simple.blog.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 登录授权过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(Constants.JWT_HEADER);

        if (null != header && header.startsWith(Constants.JWT_AUTHENTICATION_SCHEMA)) {
            //  获取 token 和用户名
            String token = header.substring(Constants.JWT_AUTHENTICATION_SCHEMA.length());
            String username = JwtUtil.getUsernameFromToken(token);

            //  username 不为空，并且未认证
            if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                //  获取登录用户信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //  判断 token 是否有效
                if (JwtUtil.validateToken(token, userDetails)) {
                    //  根据用户信息生成 authentication，并将 authentication 放入安全上下文
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
