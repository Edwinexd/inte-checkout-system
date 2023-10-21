package com.agie;

import java.util.Calendar;

/* A personNumber is a 10-digit number that is used to identify a person in Sweden.
 In this implementation we've decided to use 12 digits instead of 10.
 This makes the format as following
 YYYYMMDDXXXX
 The first 8 digits are the date of birth.
 The last 4 digits are a serial number that is used to differentiate people born on the same day.
 */

public class Customer {
    private final long pnr;
    private final String pnrString;
    private final String pnrYear;
    private final String pnrMonth;
    private final String pnrDay;
    private final boolean isLeapYear;

    public Customer(long personNumber) {
        if (personNumber < 0) {
            throw new IllegalArgumentException("personNumber can't be negative");
        }
        if (!isLengthOfPnrValid(personNumber)) {
            throw new IllegalArgumentException("Personnummer has to be 12 in length");
        }

        this.pnr = personNumber;
        pnrString = "" + personNumber;

        pnrYear = pnrString.substring(0, 4);
        pnrMonth = pnrString.substring(4, 6);
        pnrDay = pnrString.substring(6, 8);
        // We can't validate the last 4 digits.

        if (isDateInFuture(Integer.parseInt(pnrYear), Integer.parseInt(pnrMonth), Integer.parseInt(pnrDay))) {
            throw new IllegalArgumentException("Personnummer can't be in the future");
        }

        isLeapYear = isYearALeapYear(Integer.parseInt(pnrYear));

        if (!isDayInMonthValid(isLeapYear, Integer.parseInt(pnrMonth), Integer.parseInt(pnrDay))) {
            throw new IllegalArgumentException("Day is not valid for the month");
        }
    }

    private boolean isLengthOfPnrValid(Long personNumber) {
        String pnrString = "" + personNumber;
        return pnrString.length() == 12;
    }

    private boolean isDateInFuture(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        if (year > calendar.get(Calendar.YEAR)) {
            return true;
        }
        if (year < calendar.get(Calendar.YEAR)) {
            return false;
        }

        if (month > calendar.get(Calendar.MONTH)) {
            return true;
        }
        if (month < calendar.get(Calendar.MONTH)) {
            return false;
        }

        return day > calendar.get(Calendar.DAY_OF_MONTH);
    }

    // Checks if the day is valid for the month.
    // EXAMPLE: 31st of February is not a valid date.
    // EXAMPLE: 29th of February is only valid if the year is a leap year.
    private boolean isDayInMonthValid(boolean isLeapYear, int month, int day) {
        int daysInMonth;
        if (month == 2) {
            if (isLeapYear) {
                daysInMonth = 29;
            } else {
                daysInMonth = 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else {
            daysInMonth = 31;
        }

        return day <= daysInMonth;
    }

    private boolean isYearALeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public long getPnr() {
        return this.pnr;
    }
}
