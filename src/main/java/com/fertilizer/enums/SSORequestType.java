package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SSORequestType {
	LOGIN("login"), SIGNUP("signup"),FORGOTPASSWORD("forgot-password"),UPDATEPROFILE("update-profile"),SIGNUPOTP("sigup-otp");

	private final String name;

	private SSORequestType(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static SSORequestType forWholeDayName(String name) {
		for (SSORequestType state : SSORequestType.values()) {
			if (state.getName().equalsIgnoreCase(name)) {
				return state;
			}
		}
		throw new IllegalArgumentException("Unknown whole Day name " + name);
	}
	
	public static SSORequestType fromShortName(String shortName) {
		switch (shortName) {
		case "login":
			return SSORequestType.LOGIN;

		case "signup":
			return SSORequestType.SIGNUP;
			
		case "forgot-password":
			return SSORequestType.FORGOTPASSWORD;
			
		case "update-profile":
			return SSORequestType.UPDATEPROFILE;
		case "sigup-otp":
			return SSORequestType.SIGNUPOTP;

		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}
}
