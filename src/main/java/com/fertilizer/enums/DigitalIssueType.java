package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DigitalIssueType {

    Standard("Standard Websites"),Audience("Audience");
    private final String name;

    DigitalIssueType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    @JsonCreator
    public static DigitalIssueType forWholeDayName(String name) {
        for (DigitalIssueType state : DigitalIssueType.values()) {
            if (state.getName().equalsIgnoreCase(name)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown whole Digital Rate type " + name);
    }

    public static DigitalIssueType fromShortName(String shortName) {
        switch (shortName.toLowerCase()) {
            case "standard websites":
                return DigitalIssueType.Standard;
            case "audience":
                return DigitalIssueType.Audience;

            default:
                throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
        }
    }
}

