package com.fertilizer.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fertilizer.enums.Activity;
import com.fertilizer.enums.EntityConstant;
import com.fertilizer.model.UserActivityLogs;

/**
 * @author Dhiraj
 *
 */
@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LogManager.getLogger(LoggerInterceptor.class);
	
	@Autowired
	private LoggerActivityConfiguration loggerActivityConfiguration;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("[preHandle][" + request + "][" + request.getMethod() + "]" + request.getRequestURI()
		+ String.join("&_psip=", LoggerUtil.getParameters(request),LoggerUtil.getRemoteAddr(request)));
		return super.preHandle(request, response, handler);
	}

	
	public void saveUserActivityLog(HttpServletRequest request, HttpServletResponse response,
			UserActivity userActivityTypes, List<Long> logId, Long entityId) {
		UserActivityLogs userActivityLogs = new UserActivityLogs();
		if (null != entityId)
			userActivityLogs.setEntityId(entityId);
		userActivityLogs.setResponseCode(response.getStatus());
		userActivityLogs.setActivityId(userActivityTypes.getId());
		userActivityLogs.setActivityIdentifier(userActivityTypes.getActivityIdentifier());
		LoggerUtil.setRequestParametersToUserActivityLog(request, userActivityLogs);
		//UserActivityLogs save = userActivityLogsRepository.save(userActivityLogs);
		//logId.add(save.getId());
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		Map<String, UserActivity> activity = loggerActivityConfiguration.getActivity();
		if(null !=response.getHeader(Activity.ACTIVITY_NAME.getName()) 
				&& activity.containsKey(response.getHeader(Activity.ACTIVITY_NAME.getName()))) {
			try {
				UserActivity userActivityTypes=activity.get(response.getHeader(Activity.ACTIVITY_NAME.getName()));
				
				List<Long> logId=new ArrayList<>();
				if(null !=response.getHeader(EntityConstant.ENTITYID.getName())) {
					List<Long> idsToLog = Arrays.asList(response.getHeader(EntityConstant.ENTITYID.getName()).split("\\,")).stream().map(Long::valueOf)
							.collect(Collectors.toList());
					idsToLog.forEach(ids ->
						saveUserActivityLog(request, response, userActivityTypes, logId,ids)
					);
				}else {
					saveUserActivityLog(request, response, userActivityTypes, logId,null);
				}
				request.setAttribute(EntityConstant.LOGGID.getName(), logId.stream().map(String::valueOf).collect(Collectors.joining(",")));
			} catch (Exception e) {
				logger.catching(e);
			}
		}
		request.setAttribute(EntityConstant.BODY.getName(), null);
		response.setHeader(EntityConstant.ENTITYID.getName(), null);
		super.afterCompletion(request, response, handler, ex);
	}
	
}
