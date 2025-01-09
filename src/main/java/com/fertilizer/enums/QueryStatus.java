package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

@Getter
public enum QueryStatus {

	PENDING("0"), RESOLVED("1");

	private final String name;

	QueryStatus(String name) {
		this.name = name;
	}

	@JsonCreator
	public static QueryStatus forWholeDayName(String stateName) {
		for (QueryStatus state : QueryStatus.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown QueryStatus name " + stateName);
	}

	public static QueryStatus fromShortName(String shortName) {
		switch (shortName) {
		case "0":
			return QueryStatus.PENDING;
		case "1":
			return QueryStatus.RESOLVED;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
