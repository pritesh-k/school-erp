package com.schoolerp.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FeeCategory {
    ACADEMIC("Academic"),
    TRANSPORT("Transport"),
    ACTIVITY("Activity"),
    OTHER("Other");

    private final String displayName;

    FeeCategory(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static FeeCategory fromValue(String value) {
        for (FeeCategory category : values()) {
            // accept both "ACADEMIC" (enum name) and "Academic" (display name)
            if (category.name().equalsIgnoreCase(value) ||
                    category.displayName.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid FeeCategory: " + value);
    }
}
