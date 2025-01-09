package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserType {
	A("a"),
	C("c");
	
	private final String name;

	UserType(String name) {
		this.name = name;
	}
	
	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static UserType forWholeDayName(String stateName) {
		for (UserType state : UserType.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + stateName);
	}
	
	public static UserType fromShortName(String shortName) {
		switch (shortName) {
		case "a":
			return UserType.A;
		case "c":
			return UserType.C;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
