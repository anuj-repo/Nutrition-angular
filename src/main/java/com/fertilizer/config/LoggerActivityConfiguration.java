package com.fertilizer.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fertilizer.util.TemplateDocumentUtil;

/**
 * @author Dhiraj
 *
 */
@Component
public class LoggerActivityConfiguration implements CommandLineRunner {

	private static final Map<String, UserActivity> activity = new ConcurrentHashMap<>();

	@Override
	public void run(String... args) throws Exception {
		List<UserActivity> findAll =TemplateDocumentUtil.getUserActivityTypesByfile();
		findAll.forEach(userActivityType -> activity.put(userActivityType.getActivityIdentifier(), userActivityType));
	}

	public Map<String, UserActivity> getActivity() {
		return activity;
	}
	
	
}
