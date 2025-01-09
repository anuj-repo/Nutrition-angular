package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RadioDayPart {
	MORNING_TIME_BAND("1"), AFTERNOON_TIME_BAND("2"), EVENING_TIME_BAND("3"), DRIVE_TIME("4"),RODP("5"),
	ROS("6"),ANYOTHERSPECIFIC("7");

	private final String name;

	private RadioDayPart(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static RadioDayPart forWholeDayName(String name) {
		for (RadioDayPart state : RadioDayPart.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Radio Day Part " + name);
	}
	
	public static RadioDayPart fromShortName(String shortName) {
		switch (shortName) {
		case "1":
			return RadioDayPart.MORNING_TIME_BAND;

		case "2":
			return RadioDayPart.AFTERNOON_TIME_BAND;
			
		case "3":
			return RadioDayPart.EVENING_TIME_BAND;

		case "4":
			return RadioDayPart.DRIVE_TIME;
			
		case "5":
			return RadioDayPart.RODP;

		case "6":
			return RadioDayPart.ROS;
			
		case "7":
			return RadioDayPart.ANYOTHERSPECIFIC;
			
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
	
	public static RadioDayPart fromSheetShortName(String shortName) {
		switch (shortName) {
		case "0-morning time band":
			return RadioDayPart.MORNING_TIME_BAND;

		case "1-afternoon time band":
			return RadioDayPart.AFTERNOON_TIME_BAND;
			
		case "2-evening time band":
			return RadioDayPart.EVENING_TIME_BAND;

		case "3-drive time":
			return RadioDayPart.DRIVE_TIME;
			
		case "4-rodp":
			return RadioDayPart.RODP;

		case "5-ros":
			return RadioDayPart.ROS;
			
		case "6-any specific time":
			return RadioDayPart.ANYOTHERSPECIFIC;

		
			
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
