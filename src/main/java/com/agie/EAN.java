package com.agie;

public class EAN {
    private static final int EAN_LENGTH = 13;

    private final long number;
    private final int countryCode, checkDigit;

    public EAN (long number) {
        if (number < 0) {
            throw new IllegalArgumentException("EAN may not be negative");
        }
        if (Long.toString(number).length() != EAN_LENGTH) {
            throw new IllegalArgumentException("Only EAN-13 supported");
        }

        int checkDigit = (int) (number % 10);

        if (getCheckDigit(number) != checkDigit) {
            throw new IllegalArgumentException("Invalid check digit");
        }

        // validate country code
        int countryCode = (int) (number / 10000000000L);

        if (!EANCountryCodes.isValidCountryCode(countryCode)) {
            throw new IllegalArgumentException("Invalid country code");
        }

        if (EANCountryCodes.isInternalCountryCode(countryCode)) {
            throw new IllegalArgumentException("Internal country code");
        }

        this.number = number;
        this.countryCode = countryCode;
        this.checkDigit = checkDigit;
    }

    private int getCheckDigit(long number) {
        // https://boxshot.com/barcode/tutorials/ean-13-calculator/
        int evenSum = 0;
        int oddSum = 0;
        
        number /= 10; // Discard check digit

        for (int i = 0; i < EAN_LENGTH-1; i++) {
            // cast is safe as % 10 will yield a single digit reminder
            int digit = (int) (number % 10);

            number /= 10;

            if (i % 2 == 0) {
                evenSum += digit;
            } else {
                oddSum += digit;
            }
        }
        
        evenSum *= 3;

        int checkDigit = (evenSum + oddSum) % 10;
        if (checkDigit != 0) {
            checkDigit = 10 - checkDigit;
        }

        return checkDigit;
    }

    public int getCountryCode () {
        return countryCode;
    }

    public int getCheckDigit () {
        return checkDigit;
    }

    @Override
    public int hashCode () {
        return Long.hashCode(number);
    }

    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof EAN)) {
            return false;
        }
        EAN other = (EAN) obj;
        return this.number == other.number;
    }

    @Override
    public String toString () {
        return Long.toString(number);
    }
}
