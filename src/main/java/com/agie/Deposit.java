package com.agie;

public enum Deposit {
    NINTY_CENTS(0.9),
    ONE(1.0),
    TWO(2.0);

    private final Money value;

    Deposit(double value) {
        this.value = new Money(value);
    }

    public Money getValue() {
        return value;
    }
}
