package com.fertilizer.util;

public enum MessageConstant {

	INVALID_USERNAME_ANS_PASSWORD("Your Email and Password do not match. Please try again"),
	USER_NAME_ALREDYEXIST("username already exists"), UNABLE_TOLOGOUT("unable to logout."),
	LOGGED_OUT_SUCCES("Logged out successfully."),
	EXITING_USER_SIGNUP("Your account with this mail id already exists with HT. Please try to login"),
	EXITING_USER_SIGNUP_STEP2("You are already a part of Adworks, please log-in with your credential"),
	PASSWORD_CONFIRMPASSWORD_NOTSAME("Password and Confirm Password should be same"),
	SIGN_UP1_SUCCES("Verification OTP is sent on your email address."),
	MANDATORY_CLIENT("Client Id is required in case of purchase for client"),
	USER_NOT_APPROVED("This User is not approved"),
	USER_NOT_APPROVED_FOR_DIGITAL("This User is not approved for Digital"),
	USER_NOT_APPROVED_FOR_PRINT("This User is not approved for Print"),
	USER_NOT_APPROVED_FOR_RADIO("This User is not approved for Radio"),
	USER_ORDER_NOT_FOUND("User Not found for this order"),
	UNABLE_TO_CREATE_PACKAGE_PRICE("Not able to create Package Price, Please contact Support"),
	NEED_ASSISTANCE_DETAILS("Please provide Need Assistance Details or Advertisement Audio file"),
	CONTENT_UPLOADED("content uploaded by user id"), NO_FILENAME_FOR_KEY("No Filenname for the fileKey-->"),
	PACKAGE_OR_CHANNEL_DETAILS_NOT_FOUND("Please provide Package Details and Channel Name"),
	NUMBERS("0123456789"),
	INTERNAL_ORDER_NUMBER_ERROR("Internal order number should be unique and cannot match with past entries"),

	USER_DETAILS_NOT_FOUND("User detail not found.");


	private final String name;

	MessageConstant(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	};
}
