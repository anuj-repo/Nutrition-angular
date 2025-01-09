package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;


public enum Status {
	INACTIVE("0"), ACTIVE("1"), DELETED("2"), ARCHIVED("3"), DEMO("4");

	private final String name;

	Status(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public static Status forStatusName(String stateName) {
		for (Status state : Status.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown Status name " + stateName);
	}

	public static Status fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return Status.INACTIVE;

		case "1":
			return Status.ACTIVE;

		case "2":
			return Status.DELETED;

		case "3":
			return Status.ARCHIVED;

		case "4":
			return Status.DEMO;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
