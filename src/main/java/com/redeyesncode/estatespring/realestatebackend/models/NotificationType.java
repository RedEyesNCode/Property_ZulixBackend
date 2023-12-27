package com.redeyesncode.estatespring.realestatebackend.models;

public enum NotificationType {
    VIEWED("VIEWED"),
    MESSAGE("MESSAGE"),
    LISTING_STATUS("LISTING_STATUS")
    // Add more types as needed
    ;

    private final String value;

    NotificationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
