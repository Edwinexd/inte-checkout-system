package com.agie;

public class EAN {
    private static final int EAN_LENGTH = 13;

    private final long number;

    public EAN (Long number) {
        if (number == null) {
            throw new IllegalArgumentException("Number cannot be null");
        }
        if (number.toString().length() != EAN_LENGTH) {
            throw new IllegalArgumentException("Only EAN-13 supported");
        }
        if (getCheckDigit(number) != number % 10) {
            throw new IllegalArgumentException("Invalid check digit");
        }

        this.number = number;
    }

    private long getCheckDigit(long number) {
        // https://boxshot.com/barcode/tutorials/ean-13-calculator/
        long sum = 0;
        long[] digits = new long[EAN_LENGTH];
        for (int i = digits.length-1; i >= 0; i--) {
            digits[i] = number % 10;
            number /= 10;
        }
        for (int i = 0; i < digits.length; i++) {
            if (i % 2 == 0) {
                sum += digits[i];
            } else {
                sum += digits[i] * 3;
            }
        }
        long checkDigit = 10 - (sum % 10);
        if (checkDigit == 10) {
            checkDigit = 0;
        }
        return checkDigit;
    }

    @Override
    public String toString () {
        return Long.toString(number);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof EAN)) {
            return false;
        }
        EAN other = (EAN) obj;
        return this.number == other.number;
    }
}
