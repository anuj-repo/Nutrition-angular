package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PackageType {
	CUSTOM("custom"), EXISTING("existing");
	private final String name;

	private PackageType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static PackageType forWholeDayName(String name) {
		for (PackageType state : PackageType.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + name);
	}
	
	public static PackageType fromShortName(String shortName) {
		switch (shortName) {
		case "custom":
			return PackageType.CUSTOM;

		case "existing":
			return PackageType.EXISTING;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
