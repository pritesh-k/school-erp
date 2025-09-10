package com.schoolerp.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ClassStandard {
    PRE_NURSERY(1, "Pre-Nursery"),
    NURSERY(2, "Nursery"),
    LKG(3, "LKG"),
    UKG(4, "UKG"),
    FIRST(5, "First"),
    SECOND(6, "Second"),
    THIRD(7, "Third"),
    FOURTH(8, "Fourth"),
    FIFTH(9, "Fifth"),
    SIXTH(10, "Sixth"),
    SEVENTH(11, "Seventh"),
    EIGHTH(12, "Eighth"),
    NINTH(13, "Ninth"),
    TENTH(14, "Tenth"),
    ELEVENTH(15, "Eleventh"),
    TWELFTH(16, "Twelfth"),
    PLAYGROUP(0, "Playgroup"),
    PRE_PRIMARY(0, "Pre-Primary"),
    PRIMARY(0, "Primary");

    private final int order;
    private final String readableName;

    ClassStandard(int order, String readableName) {
        this.order = order;
        this.readableName = readableName;
    }

    public int getOrder() {
        return order;
    }

    @JsonValue
    public String getReadableName() {
        return readableName;
    }

    @JsonCreator
    public static ClassStandard fromValue(String value) {
        for (ClassStandard standard : values()) {
            // Accept both enum name (PRE_NURSERY) and readable name (Pre-Nursery)
            if (standard.name().equalsIgnoreCase(value) ||
                    standard.readableName.equalsIgnoreCase(value)) {
                return standard;
            }
        }
        throw new IllegalArgumentException("Invalid ClassStandard: " + value);
    }
}
