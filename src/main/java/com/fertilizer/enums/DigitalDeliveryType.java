package com.fertilizer.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DigitalDeliveryType {
    ROS("ros"),ROADBLOCK("roadblock");
    private final String name;

    DigitalDeliveryType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    public static DigitalDeliveryType fromShortName(String shortName) {
        switch (shortName) {
            case "ros":
                return DigitalDeliveryType.ROS;

            case "roadblock":
                return DigitalDeliveryType.ROADBLOCK;

            default:
                throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
        }
    }
}
