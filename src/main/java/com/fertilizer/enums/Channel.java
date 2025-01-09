package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Channel {
	PRINT("print"), RADIO("radio"), DIGITAL("digital");

	private final String name;

	private Channel(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	public static Channel fromShortName(String shortName) {
		switch (shortName) {
		case "print":
			return Channel.PRINT;

		case "radio":
			return Channel.RADIO;

		case "digital":
			return Channel.DIGITAL;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
