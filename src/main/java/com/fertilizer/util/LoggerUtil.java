package com.fertilizer.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fertilizer.constants.ApplicationConstants;


/**
 * @author Prashant
 *
 */
@Component
public final class LoggerUtil {
	private static final Logger logger = LogManager.getLogger(LoggerUtil.class);
	@Value("${log.request.forResponseCodes}")
	private String responseCodestoLog;
	private LoggerUtil() {}
	
	
	public static String getServerIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
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

	public boolean shallRequestBeLogged(String requestUrl, String responseStatus) {
		 for (String str : ApplicationConstants.IGNORE_URLS_REQUEST_LOG) {
		        if (requestUrl.toLowerCase().contains(str.toLowerCase())) {
		            return false;
		        }
		    }
		 if(responseCodestoLog == null) return false;
		 String responseCodes[] = responseCodestoLog.split(",");
		 removeSpaces(responseCodes);
		 for (String responseCode : responseCodes) {
		        if (responseCode.toLowerCase().equals(responseStatus.toLowerCase()) || responseCode.equals("*")) {
		            return true;
		        }
		    }
		 
		return false;
	}
	
	public static void removeSpaces(String[] array) {
	    for (int i = 0; i < array.length; i++) {
	        array[i] = array[i].replaceAll("\\s", "");
	    }
	}
}
