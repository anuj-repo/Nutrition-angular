package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountType {
	AGENCY("agency"), COMPANY("company");

	private final String name;

	private AccountType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static AccountType forWholeDayName(String name) {
		for (AccountType state : AccountType.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + name);
	}
	
	public static AccountType fromShortName(String shortName) {
		switch (shortName) {
		case "agency":
			return AccountType.AGENCY;

		case "company":
			return AccountType.COMPANY;
			

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
