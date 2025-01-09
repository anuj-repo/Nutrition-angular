package com.fertilizer.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fertilizer.model.ApplicationLog;
import com.fertilizer.repository.ApplicationLogRepository;
import com.fertilizer.util.LoggerUtil;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

	@Autowired
	private ApplicationLogRepository logRepository;

	@Autowired
	private LoggerUtil loggerUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final Logger logger = LogManager.getLogger(LoggerUtil.class);
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		ApplicationLog logEntity = new ApplicationLog();

		try {
			try {
				filterChain.doFilter(requestWrapper, responseWrapper);
			} catch (Exception e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				String exceptionStackTrace = sw.toString().substring(0, 2000);
				logEntity.setApplicationError(exceptionStackTrace);
				// You might also consider re-throwing the exception here if further handling is
				// needed
			}
		} catch (RuntimeException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exceptionStackTrace = sw.toString().substring(0, 2000);
			logEntity.setApplicationError(exceptionStackTrace);
			// You might also consider re-throwing the exception here if further handling is
			// needed

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			String requestBody = getContentAsString(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
			String responseBody = getContentAsString(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
			String requestUrl = request.getRequestURL().toString();
			String responseStatus = Integer.toString(response.getStatus());
			if (loggerUtil.shallRequestBeLogged(requestUrl, responseStatus)) {
				// Save the request and response data to the database table
				logEntity.setRequestMethod(request.getMethod());
				logEntity.setRequestUri(request.getRequestURL().toString());
				logEntity.setRequestPayload(requestBody.substring(0, requestBody.length() > 5000? 5000:requestBody.length()));
				logEntity.setResponseStatus(responseStatus);
				logEntity.setResponse(responseBody.substring(0, responseBody.length() > 5000? 5000:responseBody.length()));
				
				logEntity.setClientIp(LoggerUtil.getRemoteAddr(requestWrapper));
				logEntity.setServerIp(LoggerUtil.getServerIpAddress());
				// Update the response with the cached content
				logRepository.save(logEntity);
			}

			responseWrapper.copyBodyToResponse();
		}
	}

	private String getContentAsString(byte[] content, Charset charset) {
		if (content != null && content.length > 0) {
			return new String(content, charset);
		}
		return "";
	}
}
