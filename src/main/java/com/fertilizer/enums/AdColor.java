package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AdColor {

	Color("color");
	private String name;

	private AdColor(String name) {
		this.name = name;
	}
	@JsonValue
	public String getName(){
		return this.name;
	}
	public static boolean isValidAdColor(String stateName) {
		for (AdColor state : AdColor.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return true;
			}
		}
		return false;
	}
	public static AdColor fromShortName(String shortName) {
		switch (shortName) {
		
		case "color":
			return AdColor.Color;
		
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
