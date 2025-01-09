package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ClientType {
	LTL("ltl"), NTL("ntl");

	private final String name;

	private ClientType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static ClientType forWholeDayName(String name) {

		for (ClientType state : ClientType.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + name);
	}
	
	public static ClientType fromShortName(String shortName) {
		switch (shortName) {
		case "ltl":
			return ClientType.LTL;

		case "ntl":
			return ClientType.NTL;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
