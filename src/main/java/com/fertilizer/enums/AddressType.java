package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AddressType {
	BILLING("billing");
	
	private final String name;

	AddressType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public static AddressType forStatusName(String stateName) {
		for (AddressType state : AddressType.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown Admin Address Type " + stateName);
	}

	public static AddressType fromShortName(String shortName) {
		switch (shortName) {
		case "billing":
			return AddressType.BILLING;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
