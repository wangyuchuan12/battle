package com.wyc.common.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtil {
    final static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
    
    /**
     * 获取用户真实IP地址，需代理服务器发送正确的 header
     */
    public static String getUserIp(HttpServletRequest request) {
        String ipAddress = null;
        //ipAddress = request.getRemoteAddr();
        ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));   
            }
        }
        return ipAddress; 
    }
}
