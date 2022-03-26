package com.simple.blog.common.constants;

public interface Constants {

    /**
     * JWT 加解密使用的密钥
     */
    String JWT_SECRET = "simple-blog-secret";

    /**
     * JWT 的超期限时间(7*24*60*60)
     */
    Long JWT_EXPIRATION = 604800L;

    /**
     * JWT 请求头
     */
    String JWT_HEADER = "Authorization";

    /**
     * JWT 身份验证模式
     */
    String JWT_AUTHENTICATION_SCHEMA = "Bearer";

}
