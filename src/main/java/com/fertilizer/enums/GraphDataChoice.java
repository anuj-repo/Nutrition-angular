package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GraphDataChoice {
	DAY("day"), MONTH("month"), WEEK("week"),YEAR("year");
	
	private final String name;

	private GraphDataChoice(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	public static GraphDataChoice fromShortName(String shortName) {
		switch (shortName) {
		case "year":
			return GraphDataChoice.YEAR;

		case "month":
			return GraphDataChoice.MONTH;

		case "week":
			return GraphDataChoice.WEEK;
			
		case "day":
			return GraphDataChoice.DAY;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
