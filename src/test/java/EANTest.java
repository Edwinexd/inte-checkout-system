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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.agie.EAN;

public class EANTest {
    public static final long VALID_EAN = 1234567890128L;
    public static final long INVALID_EAN_LENGTH = 123456789012L;
    public static final long INVALID_EAN_CHECK_DIGIT = 1234567890129L;

    // Test cases made from equivalence classes
    
    // T1
    @Test
    public void constructorValidCountryCodeCheckDigitTest() {
        EAN ean = new EAN(VALID_EAN);
        assertEquals(ean.getCountryCode(), 13);
        assertEquals(ean.getCheckDigit(), 8);
    }

    // T2
    @Test
    public void constructorLessThan13DigitsThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EAN(1L);
        });
    }

    // T3
    @Test
    public void constructorMoreThan13DigitsThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EAN(12345678901289L);
        });
    }

    // T4
    @Test
    public void constructorNegativeLength13Throws() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EAN(-123456789012L);
        });
    }

    // T5
    // Can't be implemented as numbers cant have 0 as first digit in Java

    // T6 & T7

    @Test
    public void constructorIncorrectCheckDigitThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EAN(INVALID_EAN_CHECK_DIGIT);
        });
    }

    // T8
    @Test
    public void constructorInvalidCountryCodeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EAN(7194567890129L);
        });
    }

    // T9
    @Test
    public void constructorInternalCountryCodeThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EAN(2004567890126L);
        });
    }

    // T10
    @Test
    public void constructorCheckDigitZeroTest() {
        EAN ean = new EAN(1234567890050L);
        assertEquals(ean.getCountryCode(), 123);
        assertEquals(ean.getCheckDigit(), 0);
    }


    // End of equivalence class test cases

    @Test
    public void equalsTest() {
        EAN ean1 = new EAN(VALID_EAN);
        EAN ean2 = new EAN(VALID_EAN);
        assertEquals(ean1, ean2);
    }

    @Test
    public void equalsBothEANDifferentValueTest() {
        EAN ean1 = new EAN(VALID_EAN);
        EAN ean2 = new EAN(1234567890050L);
        assertFalse(ean1.equals(ean2));
    }

    @Test
    public void equalsOtherObjectTest() {
        EAN ean = new EAN(VALID_EAN);
        assertFalse(ean.equals(new Object()));
    }

    @Test
    public void hashCodeTest() {
        EAN ean = new EAN(VALID_EAN);
        assertEquals(Long.hashCode(VALID_EAN), ean.hashCode());
    }

    @Test
    public void toStringTest() {
        EAN ean = new EAN(VALID_EAN);
        assertEquals(Long.toString(VALID_EAN), ean.toString());
    }

}
