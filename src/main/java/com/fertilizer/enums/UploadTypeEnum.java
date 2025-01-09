package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UploadTypeEnum {

	FILE("file"), URL("url"),;

	private final String name;

	private UploadTypeEnum(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	public static UploadTypeEnum fromShortName(String shortName) {
		switch (shortName) {
		case "file":
			return UploadTypeEnum.FILE;

		case "url":
			return UploadTypeEnum.URL;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}