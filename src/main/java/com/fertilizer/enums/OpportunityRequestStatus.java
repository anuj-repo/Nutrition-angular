package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OpportunityRequestStatus {

    FAILURE("0"), SUCCESS("1"), NOTSENT("2");

    private final String name;

    private OpportunityRequestStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    public static OpportunityRequestStatus fromShortName(String shortName) {
        switch (shortName) {

            case "0":
                return OpportunityRequestStatus.FAILURE;

            case "1":
                return OpportunityRequestStatus.SUCCESS;

            case "2":
                return OpportunityRequestStatus.NOTSENT;


            default:
                throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
        }
    }
}


