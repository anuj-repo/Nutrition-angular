package com.fertilizer.enums;

public enum RateDay {
	WEEKDAY("weekday"),WEEKEND("weekend"),WEEK("week"),ALL("all"),SATURDAY("saturday"),SUNDAY("sunday");
	private String name;

	private RateDay(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	
	public static RateDay fromShortName(String shortName) {
		switch(shortName) {
		case "weekday":
			return RateDay.WEEKDAY;

		case "weekend":
			return RateDay.WEEKEND;
			
		case "week":
			return RateDay.WEEK;
			
		case "all":
			return RateDay.ALL;
			
		case "saturday":
				return RateDay.SATURDAY;
				
		case "sunday":
				return RateDay.SUNDAY;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
		}
}
