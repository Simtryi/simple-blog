package com.simple.blog.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.simple.blog.common.constants.Constants;
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
 *  JWT token的格式：header.payload.signature：
 *  header：签名的生成算法，{"alg": "HS512"}。
 *  payload：用户名、token的生成时间和过期时间，{"sub": "admin", "created": 1489079981393, "exp": 1489681781}。
 *  signature：基于header和payload生成的签名，一旦header和payload被篡改，验证将失败。
 * </p>
 */
public class JWTUtil {

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
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从 token 中获取用户名
     */
    public static String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从 token 中获取过期时间
     */
    private static Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 解析 token
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Constants.JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            //  todo 打印异常信息
            //  LOGGER.info("JWT格式验证失败：{}", token);
        }
        return claims;
    }

    /**
     * 刷新 token
     */
    public static String refreshToken(String oldToken) {
        if (StrUtil.isEmpty(oldToken)) {
            return null;
        }

        String token = oldToken.substring(Constants.JWT_TOKEN_HEAD.length());
        if (StrUtil.isEmpty(token)) {
            return null;
        }

        Claims claims = getClaimsFromToken(token);
        if (null == claims) {
            return null;
        }

        //  校验 token 是否过期
        if (isTokenExpired(token)) {
            return null;
        }

        //  判断 token 是否在30分钟内刷新过
        if (isTokenRefreshBefore(token, 30 * 60)) {
            return token;
        } else {
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }
    }

    /**
     * 判断 token 是否在指定时间内刷新过
     */
    private static boolean isTokenRefreshBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        return refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, time));
    }

}
