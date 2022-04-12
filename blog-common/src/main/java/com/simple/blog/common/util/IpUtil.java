package com.simple.blog.common.util;

import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.exception.ApiException;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP 工具
 */
public class IpUtil {

    /**
     * 未知 IP
     */
    private static final String UNKNOWN = "unknown";

    /**
     * 获取登录用户 IP 地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (null == ip || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        String comma = ",";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }

        String localhost = "127.0.0.1";
        if (localhost.equals(ip)) {
            try {
                //  获取本机真正的 IP 地址
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                throw new ApiException(ResultCode.UNKNOWN, e.getMessage());
            }
        }

        return ip;
    }

}
