package com.agie;

import java.util.Calendar;

/* A personal number (pnr) is a 10-digit number that is used to identify a person in Sweden.
 In this implementation we've decided to use 12 digits instead of 10.
 This makes the format as following:
 YYYYMMDDXXXX
 The first 8 digits are the date of birth.
 The last 4 digits are a serial number that is used to differentiate people born on the same day.
 */

public class Customer {
    private final long pnr;
    private final int age;

    public Customer(final long personalNumber) {
        if (personalNumber < 0) {
            throw new IllegalArgumentException("person number can't be negative");
        }
        if (!isLengthOfPnrValid(personalNumber)) {
            throw new IllegalArgumentException("Person number has to be 12 in length");
        }

        final String pnrString = "" + personalNumber;
        final int pnrYear = Integer.parseInt(pnrString.substring(0, 4));

        if (pnrYear < 1900) {
            throw new IllegalArgumentException("A person can't be born before the year 1900 and be a customer");
        }

        final int pnrMonth = Integer.parseInt(pnrString.substring(4, 6)); // 04 will parse as 4
        final int pnrDay = Integer.parseInt(pnrString.substring(6, 8));
        // We can't validate the last 4 digits.

        if (isDateInFuture(pnrYear, pnrMonth, pnrDay)) {
            throw new IllegalArgumentException("Personal number can't be in the future");
        }

        if (!isDayInMonthValid(pnrYear, pnrMonth, pnrDay)) {
            throw new IllegalArgumentException("Day is not valid for the month");
        }
        this.pnr = personalNumber;
        this.age = getAgeByPersonalNumber(pnrYear, pnrMonth, pnrDay);
    }

    private int getAgeByPersonalNumber(final int year, final int month, final int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1); // Since Calendar uses 0-indexed months, we have to add 1 to the month.

        int age = calendar.get(Calendar.YEAR) - year;
        if (month <= calendar.get(Calendar.MONTH)) {
            if (month == calendar.get(Calendar.MONTH)) {
                if (day > calendar.get(Calendar.DAY_OF_MONTH)) {
                    age--;
                }
            }
        } else {
            age--;
        }
        return age;
    }

    // A Personal Number has to be positive and 12 digits in length.
    // EXAMPLE: 199001011234 is a valid personal number.
    private boolean isLengthOfPnrValid(final Long personalNumber) {
        final String pnrString = "" + personalNumber;
        return pnrString.length() == 12;
    }

    // Checks if the provided date is in the future.
    // Returns true if the date is in the future, otherwise false.
    // EXAMPLE: 2025-05-06 is in the future.
    private boolean isDateInFuture(final int year, final int month, final int day) {
        // Create a Calendar instance representing the current date and time.
        final Calendar calendar = Calendar.getInstance();

        // Since Calendar uses 0-indexed months, we have to add 1 to the month.
        calendar.add(Calendar.MONTH, 1);

        // If the provided year is greater than the current year, it is in the future, therefore return true.
        if (year > calendar.get(Calendar.YEAR)) {
            return true;
        }
        // If the years are the same, check the month.
        else if (year == calendar.get(Calendar.YEAR)) {
            // If the provided month is greater than the current month, return true.
            if (month > calendar.get(Calendar.MONTH)) {
                return true;
            }
            // If the provided month is the same as the current month, check the day.
            if (month == calendar.get(Calendar.MONTH)) {
                // If the provided day is greater than the current day, return true.
                return day > calendar.get(Calendar.DAY_OF_MONTH);
            }
        }
        // If none of the conditions are met, the provided date is not in the future, so return false.
        return false;
    }

    // Checks if the day is valid for the month.
    // EXAMPLE: 31st of February is not a valid date.
    // EXAMPLE: 29th of February is only valid if the year is a leap year.
    private boolean isDayInMonthValid(final int pnrYear, final int month, final int day) {
        int daysInMonth;
        if (month == 2) { // February
            if (isYearALeapYear(pnrYear)) {
                daysInMonth = 29;
            } else {
                daysInMonth = 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else {
            daysInMonth = 31;
        }

        if (day < 1) {
            throw new IllegalArgumentException("Day can't be less than 1");
        }
        return day <= daysInMonth;
    }

    // Is the given year a leap year?
    // Returns true if so, otherwise false.
    private boolean isYearALeapYear(final int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public long getPnr() {
        return this.pnr;
    }

    public int getAge() {
        return this.age;
    }
}
