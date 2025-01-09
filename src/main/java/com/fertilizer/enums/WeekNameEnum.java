package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WeekNameEnum {
	Monday("Monday"),Tuesday("Tuesday"),Wednesday("Wednesday"),Thursday("Thursday"),Friday("Friday"),Saturday("Saturday"),Sunday("Sunday");
	private String name;

	private WeekNameEnum(String name) {
		this.name = name;
	}
	@JsonValue
	public String getName() {
		return this.name;
	}
	
	public static WeekNameEnum fromShortName(String shortName) {
		switch (shortName) {
		case "Monday":
			return WeekNameEnum.Monday;
		case "Tuesday":
			return WeekNameEnum.Tuesday;
		case "Wednesday":
			return WeekNameEnum.Wednesday;
		case "Thursday":
			return WeekNameEnum.Thursday;	
		case "Friday":
			return WeekNameEnum.Friday;
		case "Saturday":
			return WeekNameEnum.Saturday;	
		case "Sunday":
			return WeekNameEnum.Sunday;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
