package com.schoolerp.enums;

public enum ClassStandard {
    PRE_NURSERY(1),
    NURSERY(2),
    LKG(3),
    UKG(4),
    FIRST(5),
    SECOND(6),
    THIRD(7),
    FOURTH(8),
    FIFTH(9),
    SIXTH(10),
    SEVENTH(11),
    EIGHTH(12),
    NINTH(13),
    TENTH(14),
    ELEVENTH(15),
    TWELFTH(16),
    PLAYGROUP(0),       // Optional: treat specially if needed
    PRE_PRIMARY(0),     // Same here
    PRIMARY(0);

    private final int order;

    ClassStandard(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}

