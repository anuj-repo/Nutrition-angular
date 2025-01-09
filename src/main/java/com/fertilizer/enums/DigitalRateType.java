package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DigitalRateType {

    CPC("cpc"),CPD("cpd"),CPM("cpm");
    private final String name;

    DigitalRateType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    @JsonCreator
    public static DigitalRateType forWholeDayName(String name) {
        for (DigitalRateType state : DigitalRateType.values()) {
            if (state.getName().equalsIgnoreCase(name)) {
                return state;
            }
        }
        throw new IllegalArgumentException("Unknown whole Digital Rate type " + name);
    }

    public static DigitalRateType fromShortName(String shortName) {
        switch (shortName) {
            case "cpc":
                return DigitalRateType.CPC;
            case "cpd":
                return DigitalRateType.CPD;
            case "cpm":
                return DigitalRateType.CPM;

            default:
                throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
        }
    }
}

