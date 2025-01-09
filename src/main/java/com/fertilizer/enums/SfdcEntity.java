package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SfdcEntity {

	CREATEUSER("create-user"), UPDATEUSER("update-user"), CREATEOPPORTUNITY("create-opportunity"),
	UPDATEOPPORTUNITY("update-opportunity"), LOGIN("login"), APPROVECLIENT("approve-client"), ADWORKS("adworks"),
	UPDATESFDCID("update-sfdcId"),FETCHPRINTRATE("fetch-print-rate"),
	SFDC("sfdc"),FETCHDIGITALRATE("fetch-digital-rate"), AUTHURL("oauth2/token"), CREATE_PRINT_OPPORTUNITY_URL("apexrest/CreateAdworksOpportunity");

	// REJECTCLIENT("reject-client"), DEACTIVATECLIENT("deactivate-client")
	private final String name;

	private SfdcEntity(String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	public static SfdcEntity fromShortName(String shortName) {
		switch (shortName) {
		case "create-user":
			return SfdcEntity.CREATEUSER;

		case "update-sfdcId":
			return SfdcEntity.UPDATESFDCID;

		case "update-user":
			return SfdcEntity.UPDATEUSER;

		case "create-opportunity":
			return SfdcEntity.CREATEOPPORTUNITY;

		case "update-opportunity":
			return SfdcEntity.UPDATEOPPORTUNITY;

		case "login":
			return SfdcEntity.LOGIN;

		case "approve-client":
			return SfdcEntity.APPROVECLIENT;

		case "adworks":
			return SfdcEntity.ADWORKS;

		case "sfdc":
			return SfdcEntity.SFDC;

		case "fetch-digital-rate":
			return SfdcEntity.FETCHDIGITALRATE;

		case "fetch-print-rate":
			return SfdcEntity.FETCHPRINTRATE;
		default:
			throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
		}
	}

}
