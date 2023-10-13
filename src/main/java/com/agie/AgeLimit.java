package com.agie;

public enum AgeLimit {
    AGE_LIMIT_15(15),
    AGE_LIMIT_18(18);

    private final int ageLimit;

    AgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public int getAgeLimit() {
        return ageLimit;
    }
}
