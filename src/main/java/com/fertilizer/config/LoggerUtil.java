package com.fertilizer.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fertilizer.enums.EntityConstant;
import com.fertilizer.enums.Status;
import com.fertilizer.model.UserActivityLogs;

/**
 * @author Dhiraj
 *
 */
public final class LoggerUtil {
	private static final Logger logger = LogManager.getLogger(LoggerUtil.class);
	private LoggerUtil() {}
	
	public static void setRequestParametersToUserActivityLog(HttpServletRequest request, UserActivityLogs userActivityLogs) {
		userActivityLogs.setRequestIp(getRemoteAddr(request));
		userActivityLogs.setMacAddress(getClientMacAddress(request));
		userActivityLogs.setRequestDevice(request.getHeader("device"));
		userActivityLogs.setRequestMethod(request.getMethod());
		if(null !=request.getAttribute(EntityConstant.BODY.getName()))
			userActivityLogs.setRequestParams(request.getAttribute(EntityConstant.BODY.getName()).toString());
		userActivityLogs.setRequestUri(request.getRequestURI()+getParameters(request));
		userActivityLogs.setStatus(Status.ACTIVE);
	}
	
	public static String getParameters(HttpServletRequest request) {
		StringBuilder posted = new StringBuilder();
		Enumeration<?> e = request.getParameterNames();
		if (e != null && null !=request.getQueryString()) {
			posted.append("?");
		}
		while (null !=e && e.hasMoreElements()) {
			if (posted.length() > 1) {
				posted.append("&");
			}
			String curr = (String) e.nextElement();
			posted.append(curr + "=");
			if (curr.contains("password") || curr.contains("pass") || curr.contains("pwd")) {
				posted.append("*****");
			} else {
				posted.append(request.getParameter(curr));
			}
		}
		return posted.toString();
	}

	public static String getRemoteAddr(HttpServletRequest request) {
		String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
		if (ipFromHeader != null && ipFromHeader.length() > 0) {
			logger.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
			return ipFromHeader;
		}
		return request.getRemoteAddr();
	}

	public static String getClientMacAddress(HttpServletRequest request) {
		try {
            InetAddress address = InetAddress.getByName(getRemoteAddr(request));
            /*
             * Get NetworkInterface for the current host and then read
             * the hardware address.
             */
            NetworkInterface ni =  NetworkInterface.getByInetAddress(address);
            if (ni != null) {
                byte[] mac = ni.getHardwareAddress();
                StringBuilder macStringBuilder =new StringBuilder();
                if (mac != null) {
                    /*
                     * Extract each array of mac address and convert it
                     * to hexadecimal with the following format
                     * 08-00-27-DC-4A-9E.
                     */
                    for (int i = 0; i < mac.length; i++) {
                        macStringBuilder.append(String.format("%02X%s",
                            mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    return macStringBuilder.toString();
                } else {
                	logger.debug("Address doesn't exist or is not accessible.");
                }
            } else {
            	logger.debug("Network Interface for the specified address is not found.");
            }
        } catch (UnknownHostException | SocketException e) {
            logger.catching(e);
        }
		return null;
	}
	
	public static String getTableName(String table) {
		String[] split = table.split("\\.");
		return Arrays.asList(split).get(split.length - 1)
				.replaceAll(CamelCaseToSnakeCaseNamingStrategy.CAMEL_CASE_REGEX,
						CamelCaseToSnakeCaseNamingStrategy.SNAKE_CASE_PATTERN)
				.toLowerCase();
	}

	public static String getServerIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}catch (UnknownHostException e) {
			return null;
		}
	}
	
}
