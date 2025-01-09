package com.fertilizer.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CommonUtils {
	private static final Logger logger = LogManager.getLogger(CommonUtils.class);

	public String convertToJson(Object jsonObject) {
		logger.debug("convertToJson method of CommonUtils called");
		ObjectMapper obj = new ObjectMapper();
		try {
			return obj.writeValueAsString(jsonObject);
		} catch (Exception e) {
			logger.debug("Error in convertToJson");
			return jsonObject.toString();
		}
	}
}
