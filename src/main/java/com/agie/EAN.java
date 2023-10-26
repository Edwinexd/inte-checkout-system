/*
Checkout System - A checkout system with focus on testing - group assignment for the INTE course at Stockholm University Autumn 2023
Copyright (C) 2023 Gusten Berghäll, Ida Laaksonen, Adrian Martvall, Edwin Sundberg 

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
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
