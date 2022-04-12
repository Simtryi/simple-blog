package com.simple.blog.security.util;

import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.constants.Constants;
import com.simple.blog.common.exception.ApiException;
import com.simple.blog.common.util.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具
 * <p>
 *  JWT token 的格式：header.payload.signature：
 *  header：签名的生成算法，{"alg": "HS512"}。
 *  payload：用户名、token 的生成时间和过期时间，{"sub": "admin", "created": 1489079981393, "exp": 1489681781}。
 *  signature：基于 header 和 payload 生成的签名，一旦 header 和 payload 被篡改，验证将失败。
 * </p>
 */
public class JwtUtil {

    /**
     * 用户名声明
     */
    private static final String CLAIM_KEY_USERNAME = "sub";

    /**
     * 生成时间声明
     */
    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * 根据用户信息生成 token
     */
    public static String generateToken(UserDetails userDetails) {
        //  数据声明
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据数据声明生成 token
     */
    private static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, Constants.JWT_SECRET)
                .compact();
    }

    /**
     * 生成 token 的过期时间
     */
    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + Constants.JWT_EXPIRATION * 1000);
    }

    /**
     * 验证 token 是否有效
     */
    public static boolean validateToken(String token, UserDetails userDetails) {
        //  验证 token 是否过期
        if (isTokenExpired(token)) {
            return false;
        }

        //  验证用户名是否相同
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername());
    }

    /**
     * 判断 token 是否过期
     */
    private static boolean isTokenExpired(String token) {
        Date expiredDate = getExpirationDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从 token 中获取用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 从 token 中获取过期时间
     */
    private static Date getExpirationDateFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getExpiration();
    }

    /**
     * 解析 token
     */
    private static Claims parseToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Constants.JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new ApiException(ResultCode.BAD_REQUEST, e.getMessage());
        }
        return claims;
    }

    /**
     * 刷新 token
     * @param authorization 请求头中 Authorization 的值
     */
    public static String refreshToken(String authorization) {
        if (StringUtil.isEmpty(authorization)) {
            return null;
        }

        String token = authorization.substring(Constants.JWT_AUTHENTICATION_SCHEMA.length());
        if (StringUtil.isEmpty(token)) {
            return null;
        }

        Claims claims = parseToken(token);
        if (null == claims) {
            return null;
        }

        //  校验 token 是否过期
        if (isTokenExpired(token)) {
            return null;
        }

        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

}
