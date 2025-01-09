package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum AdCategoryType {
	DISPLAYNORMAL("display_normal"),BRANDPROMOTION("brand_promotion");
	private String name;

	private AdCategoryType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public static boolean isValidAdCategoryType(String stateName) {
		for (AdCategoryType state : AdCategoryType.values()) {
			if (state.getName().equalsIgnoreCase(stateName.replace(" ", "_"))) {
				return true;
			}
		}
		return false;
	}
	
	@JsonCreator
	public static AdCategoryType forWholeDayName(String stateName) {
		for (AdCategoryType state : AdCategoryType.values()) {
			if (state.getName().equalsIgnoreCase(stateName)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown Category name " + stateName);
	}
	
	public static AdCategoryType fromShortName(String shortName) {
		switch(shortName) {
		case ("brand promotion"):
			return AdCategoryType.BRANDPROMOTION;
		
		case ("brand_promotion"):
			return AdCategoryType.BRANDPROMOTION;

		case "display normal":
			return AdCategoryType.DISPLAYNORMAL;
		
		case "display_normal":
			return AdCategoryType.DISPLAYNORMAL;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
		}
	}

