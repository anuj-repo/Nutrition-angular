package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RequestEnum {
	
	CREATEUSER("create-user"),UPDATEUSER("update-user"),CREATEOPPORTUNITY("create-opportunity"),UPDATEOPPORTUNITY("update-opportunity");
	private final String name;

	private RequestEnum(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	public static RequestEnum fromShortName(String shortName) {
		switch (shortName) {
		case "create-user":
			return RequestEnum.CREATEUSER;

		case "update-user":
			return RequestEnum.UPDATEUSER;

		case "create-opportunity":
			return RequestEnum.CREATEOPPORTUNITY;

		case "update-opportunity":
			return RequestEnum.UPDATEOPPORTUNITY;
			
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
