package com.fertilizer.enums;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BooleanStatus {
	INACTIVE("0"), ACTIVE("1"),DELETED("2"),ARCHIVED("3");

	private final String name;

	BooleanStatus(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}
	
	public static BooleanStatus fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return BooleanStatus.INACTIVE;

		case "1":
			return BooleanStatus.ACTIVE;
			
		case "2":
			return BooleanStatus.DELETED;
			
		case "3":
			return BooleanStatus.ARCHIVED;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
