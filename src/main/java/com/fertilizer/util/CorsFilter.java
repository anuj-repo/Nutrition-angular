package com.fertilizer.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dhiraj
 *
 */
public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		//response.setHeader("Access-Control-Allow-Origin", "*");

		//response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT,PATCH,HEAD");
		//response.setHeader("Access-Control-Allow-Headers", "*, Origin, X-Requested-With, Content-Type, Accept, Authorization,XMLHttpRequest, accept-encoding,accept-language");
		//response.setHeader("Access-Control-Allow-Headers", "*");
		// response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, Origin, Accept");

		
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT,PATCH,HEAD"); 
		response.setHeader("Access-Control-Max-Age", "3600"); 
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		filterChain.doFilter(servletRequest, servletResponse);

	}

}
