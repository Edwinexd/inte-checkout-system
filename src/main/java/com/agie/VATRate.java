package com.agie;

public enum VATRate {
    VAT_25(0.25),
    VAT_12(0.12),
    VAT_6(0.06),
    VAT_0(0.0);

    private final double rate;

    VATRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}