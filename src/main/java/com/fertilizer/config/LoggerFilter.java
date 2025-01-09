package com.fertilizer.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fertilizer.dao.UserActivityLogsDao;
import com.fertilizer.enums.EntityConstant;

/**
 * @author Dhiraj
 *
 */
@Component("myLoggerFilter")
public class LoggerFilter implements Filter {
	
	private static final Logger logger = LogManager.getLogger(LoggerFilter.class);

	@Autowired
	private UserActivityLogsDao userActivityLogsRepository;
	
	@Value("${logging.skip.body}")
	private String skipBody;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		if (response.getCharacterEncoding() == null) {
			response.setCharacterEncoding("UTF-8"); 
		}
		HttpServletRequest httpServletRequest=(HttpServletRequest) request;
		
		if(!checkIgnoreURLS(httpServletRequest.getRequestURI(),skipBody) && null !=httpServletRequest.getContentType() && httpServletRequest.getContentType().equals("application/json")) {
			MyRequestWrapper myRequestWrapper = new MyRequestWrapper(httpServletRequest);
			request.setAttribute(EntityConstant.BODY.getName(), myRequestWrapper.getBody());
			request=myRequestWrapper;
		}

		MyResponseWrapper myResponseWrapper = new MyResponseWrapper((HttpServletResponse) response);
		try {
			chain.doFilter(request, myResponseWrapper);
			myResponseWrapper.flushBuffer();
		} catch (Exception e) {
			logger.catching(Level.TRACE, e);
		}finally {
			try {
				byte[] copy = myResponseWrapper.getCopy();
				if (null != request.getAttribute(EntityConstant.LOGGID.getName())) {
					List<Long> idsToLog = Arrays.asList(request.getAttribute(EntityConstant.LOGGID.getName()).toString().split("\\,")).stream().map(Long::valueOf)
							.collect(Collectors.toList());
					if(!idsToLog.isEmpty())
						userActivityLogsRepository.updateUserActivityLog(idsToLog,new String(copy, response.getCharacterEncoding()));
				}
			} catch (Exception e) {
				logger.catching(Level.TRACE, e);
			}
		}
	}
	
	public boolean checkIgnoreURLS(String url,String publicUrls)
    {
    	boolean status=false;
    	List<String> ignoreURL = Arrays.asList(publicUrls.split(","));		
    	if(ignoreURL.contains(url))
    	{
    		status= true;
    	}
    	return status;
    }
}
