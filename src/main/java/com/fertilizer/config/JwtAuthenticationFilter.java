package com.fertilizer.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fertilizer.enums.BooleanEnum;
import com.fertilizer.model.UserAuthToken;
import com.fertilizer.repository.UserAuthTokenRepository;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Value("${app.public.urls}")
	private String publicUrls;

	@Value("${app.public.strict.urls}")
	private String publicStrictUrls;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private UserAuthTokenRepository userAuthTokenRepository;
	
	public static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		LOGGER.trace("doFilterInternal method called in JwtAuthenticationFilter");
		String jwt = getJwtFromRequest(request);
		String jwtStatus = "invalid";
		// check for prefetching request
		if (!request.getMethod().equals("OPTIONS") && !checkIgnoreURLS(request.getRequestURI(), publicUrls) && !authTokenNotRequiredURLS(request.getRequestURI(),publicStrictUrls) && !request.getRequestURI().startsWith("/api/voucher/pd/")) {
			if (StringUtils.hasText(jwt)) {
				jwtStatus = tokenProvider.validateToken(jwt);
			}
			if (jwtStatus.equalsIgnoreCase("valid")) {
				Long tokenId = tokenProvider.getIdFromJWT(jwt);
            	Optional<UserAuthToken> resultset = userAuthTokenRepository.findById(tokenId);
            	if(resultset.isPresent()) {
            		UserAuthToken userAuthToken = resultset.get();
            		if(userAuthToken.getIsExpired().equals(BooleanEnum.NO)) {
            			Long userId = tokenProvider.getUserIdFromJWT(jwt);
        				UserDetails userDetails = customUserDetailsService.loadUserById(userId);
        				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        						userDetails, null, userDetails.getAuthorities());
        				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        				SecurityContextHolder.getContext().setAuthentication(authentication);
            		}else {
            			response.setStatus(HttpStatus.UNAUTHORIZED.value());
        				response.sendError(HttpStatus.UNAUTHORIZED.value(), "your token have been expired.");
            		}
            	}else {
        			response.setStatus(HttpStatus.UNAUTHORIZED.value());
    				response.sendError(HttpStatus.UNAUTHORIZED.value(), "invalid token.");
        		}
            	
			} else {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.sendError(HttpStatus.UNAUTHORIZED.value(), "you don't have permission to the end point");
			}
		}
		filterChain.doFilter(request, response);
	}
	
	public boolean checkIgnoreURLS(String url, String publicUrls) {
		LOGGER.trace("checkIgnoreURLS method called in JwtAuthenticationFilter");
		boolean status = false;
		List<String> ignoreURL = Arrays.asList(publicUrls.split(","));
		if (ignoreURL.contains(url)) {
			status = true;
		}
		return status;
	}

	public boolean authTokenNotRequiredURLS(String url, String publicUrls) {
		LOGGER.trace("authTokenNotRequiredURLS method called in JwtAuthenticationFilter");
		if (!checkSwaggerURLS(url)) {
			List<String> ignoreURL = Arrays.asList(publicUrls.split(","));
			boolean status = false;
			if (ignoreURL.contains(url)) {
				status = true;
			}
			return status;
		} else {
			return true;
		}
	}

	public boolean checkSwaggerURLS(String url) {
		LOGGER.trace("checkSwaggerURLS method called in JwtAuthenticationFilter");
		boolean status = false;
		if (url.contains("/HNAP1/") || url.contains("documentation") || url.contains("/v2/api-docs")
				|| url.contains("swagger") || url.contains("/favicon.ico") || url.contains(".png")
				|| url.contains(".js") || url.contains(".gif") || url.contains(".svg") || url.contains(".jpg")
				|| url.contains(".html") || url.contains(".css")) {
			status = true;
		}
		return status;
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		LOGGER.trace("getJwtFromRequest method called in JwtAuthenticationFilter");
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
