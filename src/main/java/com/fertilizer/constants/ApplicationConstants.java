package com.fertilizer.constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ApplicationConstants {
	public static final String[] IGNORE_URLS_REQUEST_LOG = {"/documentation/", "/favicon.ico", "/swagger-resources", "/v2/api-docs"};
	public static final Map<String, String> DATE_FORMATS;
	public static final Integer AGENCY_SUPERUSER_ROLE_ID = 3;
	public static final Long PASSWORD_LINK_EXPIRE_HOURS = 48L;
	static {
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("DB_DATE", "yyyy-MM-dd");
        DATE_FORMATS = Collections.unmodifiableMap(tempMap);
    }
	
}
