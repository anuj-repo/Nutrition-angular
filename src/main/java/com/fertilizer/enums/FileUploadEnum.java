package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FileUploadEnum {
	PENDING("0"), UPLOADED("1"), UPLOADLATER("2");

	private final String name;

	FileUploadEnum(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public static FileUploadEnum forStatusName(String stateName) {
		for (FileUploadEnum state : FileUploadEnum.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown FileUploadEnum name " + stateName);
	}

	public static FileUploadEnum fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return FileUploadEnum.PENDING;

		case "1":
			return FileUploadEnum.UPLOADED;

		case "2":
			return FileUploadEnum.UPLOADLATER;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}