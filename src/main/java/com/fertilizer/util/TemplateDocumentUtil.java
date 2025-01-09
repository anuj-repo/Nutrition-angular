package com.fertilizer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fertilizer.config.UserActivity;
import com.fertilizer.enums.TemplateConstant;
import com.fertilizer.exception.TemplateNotFoundException;

/**
 * @author Dhiraj
 *
 */
public final class TemplateDocumentUtil {
	private static final Logger LOGGER = LogManager.getLogger(TemplateDocumentUtil.class);

	private TemplateDocumentUtil() {
	}

	public static Document getTemplateDocument(TemplateConstant templateConstant) {
		try {
			return Jsoup.parse(TemplateDocumentUtil.class.getResourceAsStream(templateConstant.getName()), "UTF-8", "");
		} catch (IOException e) {
			throw new TemplateNotFoundException(
					String.format("template not found with given name %s", templateConstant.getName()));
		}
	}
	
	public static List<UserActivity> getUserActivityTypesByfile(){
		try {
			return new ObjectMapper().readValue(TemplateDocumentUtil.class.getResourceAsStream("/static/user_activity.json"), new TypeReference<List<UserActivity>>(){});
		} catch (JsonParseException |JsonMappingException e) {
			LOGGER.catching(e);
		}catch (IOException e) {
			LOGGER.catching(e);
		}
		return new ArrayList<>();
	}
	
}
