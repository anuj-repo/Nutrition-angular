package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PackageTakenEnum {

	one("1000"), two("2500"), three("5000"), four("10000"), five("15000"), six("20000"), seven("25000");

	private final String name;

	private PackageTakenEnum(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	public static PackageTakenEnum fromShortName(String shortName) {
		switch (shortName) {
		case "1000":
			return PackageTakenEnum.one;

		case "2500":
			return PackageTakenEnum.two;

		case "5000":
			return PackageTakenEnum.three;

		case "10000":
			return PackageTakenEnum.four;

		case "15000":
			return PackageTakenEnum.five;

		case "20000":
			return PackageTakenEnum.six;

		case "25000":
			return PackageTakenEnum.seven;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
