package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

@Getter
public enum RadioCreativeNatureEnum {

	SAME("same"), DIFFERENT("different"), HTASSISTANCE("ht_assistance");

	private final String name;

	RadioCreativeNatureEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static RadioCreativeNatureEnum forWholeDayName(String stateName) {
		for (RadioCreativeNatureEnum state : RadioCreativeNatureEnum.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown RadioCreativeNature Enum name " + stateName);
	}

	public static RadioCreativeNatureEnum fromShortName(String shortName) {
		switch (shortName) {
		case "same":
			return RadioCreativeNatureEnum.SAME;
		case "different":
			return RadioCreativeNatureEnum.DIFFERENT;
		case "ht_assistance":
			return RadioCreativeNatureEnum.HTASSISTANCE;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
