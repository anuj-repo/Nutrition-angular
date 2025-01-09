package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PackageChoiceEnum {

	home("home"), aggriculture("aggriculture");

	private final String name;

	private PackageChoiceEnum(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	public static PackageChoiceEnum fromShortName(String shortName) {
		switch (shortName) {
		case "home":
			return PackageChoiceEnum.home;

		case "aggriculture":
			return PackageChoiceEnum.aggriculture;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
