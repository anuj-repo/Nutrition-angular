package com.fertilizer.enums;

public enum AdProductClassification {

	STANDALONE("standalone"), PACKAGE("package"), ZONING("zoning");

	private String name;

	private AdProductClassification(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public static boolean isValidAdProductClassification(String stateName) {
		for (AdProductClassification state : AdProductClassification.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return true;
			}
		}
		return false;
	}

	public static AdProductClassification fromShortName(String shortName) {

		switch (shortName) {
		case "package":
			return AdProductClassification.PACKAGE;

		case "standalone":
			return AdProductClassification.STANDALONE;

		case "zoning":
			return AdProductClassification.ZONING;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
