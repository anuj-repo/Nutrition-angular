package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserLevel {

	// Explorer → Challenger → Hero → Legend → Titan
	BEGINNER("beginner"), EXPLORER("explorer"), CHALLENGER("challenger"), HERO("hero"), LEGEND("legend"),
	TITAN("titan"), GRANDMASTER("grandmaster");

	private final String name;

	UserLevel(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public static UserLevel forStoreTypeName(String stateName) {
		for (UserLevel state : UserLevel.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown UserLavel name " + stateName);
	}

	public static UserLevel forSongTypeSubString(String stateSubString) {
		for (UserLevel state : UserLevel.values()) {
			if (state.getName().contains(stateSubString.toLowerCase())) {
				return state;
			}
		}
		return null;
	}

	public static UserLevel fromShortName(String shortName) {
		switch (shortName.toLowerCase()) {
		case "beginner":
			return UserLevel.BEGINNER;
		case "explorer":
			return UserLevel.EXPLORER;
		case "challenger":
			return UserLevel.CHALLENGER;
		case "hero":
			return UserLevel.HERO;
		case "legend":
			return UserLevel.LEGEND;
		case "titan":
			return UserLevel.TITAN;
		case "grandmaster":
			return UserLevel.GRANDMASTER;
		default:
			throw new IllegalArgumentException("Invalid UserLevel name: " + shortName);
		}
	}

	public UserLevel convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		try {
			return UserLevel.fromShortName(dbData);
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid UserLevel found in DB: " + dbData);
			throw e;
		}
	}

}
